package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.*;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.PostCategoryRepository;
import com.crewspace.api.domain.post.PostSupportRepository;
import com.crewspace.api.domain.space.Space;
import com.crewspace.api.domain.space.SpaceRepository;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.MemberCategoryRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.space.CreateSpaceRequestDTO;
import com.crewspace.api.dto.req.space.InvitationCodeRequestDTO;
import com.crewspace.api.dto.req.space.RegisterInfoRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequestDTO;
import com.crewspace.api.dto.req.space.SpaceMainRequestDTO;
import com.crewspace.api.dto.res.space.CreateSpaceResponseDTO;
import com.crewspace.api.dto.res.space.InvitationCodeResponseDTO;
import com.crewspace.api.dto.res.space.RegisterInfoResponseDTO;
import com.crewspace.api.dto.res.space.SpaceEnterResponseDTO;
import com.crewspace.api.dto.res.space.SpaceMainResponseDTO;
import com.crewspace.api.dto.res.space.SpaceMainResponseDTO.NewNotice;
import com.crewspace.api.exception.CustomException;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpaceService {

    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostSupportRepository postSupportRepository;

    @Transactional
    public CreateSpaceResponseDTO create(CreateSpaceRequestDTO createSpaceDTO) {

        Space space = createSpaceDTO.toSpace();
        spaceRepository.save(space);

        // ????????? ???????????? ?????? (?????? : ????????????, ???????????? : ??????)
        postCategoryRepository.save(createSpaceDTO.toPostCategory(space, "?????? ??????", true));
        postCategoryRepository.save(createSpaceDTO.toPostCategory(space, "??????", false));

        // ?????? ???????????? ?????? (????????? + ?????? ???)
        MemberCategory adminCategory = createSpaceDTO.toMemberCategory(space, "?????????", true);
        memberCategoryRepository.save(adminCategory);

        createSpaceDTO.getMemberCategory().forEach(
            categoryName -> memberCategoryRepository.save(createSpaceDTO.toMemberCategory(space, categoryName, false))
        );

        // ?????????_?????? ??????????????? ?????? ??????
        Member member = memberRepository.findByEmail(createSpaceDTO.getMemberEmail())
            .orElseThrow(() -> new CustomException(MEMBER_EMAIL_NOT_FOUND));

        SpaceMember spaceMember = createSpaceDTO.toSpaceMember(space, member, adminCategory);
        spaceMemberRepository.save(spaceMember);

        return CreateSpaceResponseDTO.toCreateSpaceResponseDTO(space);
    }

    public InvitationCodeResponseDTO confirmInvitationCode(InvitationCodeRequestDTO invitationCodeRequestDTO){
        Space space = spaceRepository.findByInvitationCode(
                invitationCodeRequestDTO.getInvitationCode())
            .orElseThrow(() -> new CustomException(UNVALID_SPACE_CODE));

        return InvitationCodeResponseDTO.toInvitationCodeResponseDTO(space);
    }

    public RegisterInfoResponseDTO registerInfo(RegisterInfoRequestDTO registerInfoRequestDTO){
        List<MemberCategory> memberCategories = memberCategoryRepository.findBySpaceId(
            registerInfoRequestDTO.getSpaceId());

        if(memberCategories.size() == 0 ){
            throw new CustomException(SPACE_NOT_FOUND);
        }

        return RegisterInfoResponseDTO.toRegisterInfoResponseDTO(memberCategories);
    }

    @Transactional
    public SpaceEnterResponseDTO enterSpace(SpaceEnterRequestDTO spaceEnterRequestDTO){

        Member member = memberRepository.findByEmail(spaceEnterRequestDTO.getMemberEmail())
            .orElseThrow(() -> new CustomException(MEMBER_EMAIL_NOT_FOUND));

        Boolean isExist = spaceMemberRepository.existsBySpace_IdAndMember(
            spaceEnterRequestDTO.getSpaceId(), member);

        if(isExist){
            throw new CustomException(DUPLICATE_SPACE);
        }

        MemberCategory memberCategory = memberCategoryRepository.findByIdAndSpaceId(
                spaceEnterRequestDTO.getMemberCategoryId(), spaceEnterRequestDTO.getSpaceId())
            .orElseThrow(() -> new CustomException(SPACE_OR_MEMBER_CATEGORY_NOT_FOUND));

        SpaceMember spaceMember = spaceEnterRequestDTO.toSpaceMember(member, memberCategory);
        spaceMemberRepository.save(spaceMember);

        return SpaceEnterResponseDTO.toSpaceEnterResponseDTO(spaceMember);
    }

    public SpaceMainResponseDTO spaceMain(SpaceMainRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        List<NoticePost> fixedPosts = postSupportRepository.findFixedPost(spaceMember);

        List<Tuple> results = postSupportRepository.findNewPost(spaceMember);
        List<NewNotice> newNotices = results.stream()
            .map(result -> {
                NoticePost noticePost = result.get(0, NoticePost.class);
                Long isRead = result.get(1, Long.class);
                Long isSaved = result.get(2, Long.class);

                return new NewNotice(noticePost, isRead, isSaved);
            }).collect(Collectors.toList());


        return SpaceMainResponseDTO.builder()
            .spaceMember(spaceMember)
            .fixedNotices(fixedPosts)
            .newNotices(newNotices)
            .build();
    }
}
