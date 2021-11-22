package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.*;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.post.PostCategoryRepository;
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
import com.crewspace.api.dto.res.space.CreateSpaceResponseDTO;
import com.crewspace.api.dto.res.space.InvitationCodeResponseDTO;
import com.crewspace.api.dto.res.space.RegisterInfoResponseDTO;
import com.crewspace.api.dto.res.space.SpaceEnterResponseDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
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


    @Transactional
    public CreateSpaceResponseDTO create(CreateSpaceRequestDTO createSpaceDTO) {

        Space space = createSpaceDTO.toSpace();
        spaceRepository.save(space);

        // 게시글 카테고리 생성 (공지 : 일반공지, 커뮤니티 : 일반)
        postCategoryRepository.save(createSpaceDTO.toPostCategory(space, "일반 공지", true));
        postCategoryRepository.save(createSpaceDTO.toPostCategory(space, "일반", false));

        // 회원 카테고리 생성 (운영진 + 받은 값)
        MemberCategory adminCategory = createSpaceDTO.toMemberCategory(space, "운영진", true);
        memberCategoryRepository.save(adminCategory);

        createSpaceDTO.getMemberCategory().forEach(
            categoryName -> memberCategoryRepository.save(createSpaceDTO.toMemberCategory(space, categoryName, false))
        );

        // 동아리_회원 운영진으로 자동 가입
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

        MemberCategory memberCategory = memberCategoryRepository.findByIdAndSpaceId(
                spaceEnterRequestDTO.getMemberCategoryId(), spaceEnterRequestDTO.getSpaceId())
            .orElseThrow(() -> new CustomException(SPACE_OR_MEMBER_CATEGORY_NOT_FOUND));

        SpaceMember spaceMember = spaceEnterRequestDTO.toSpaceMember(member, memberCategory);
        spaceMemberRepository.save(spaceMember);

        return SpaceEnterResponseDTO.toSpaceEnterResponseDTO(spaceMember);
    }
}
