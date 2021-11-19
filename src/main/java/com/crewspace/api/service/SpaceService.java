package com.crewspace.api.service;

import com.crewspace.api.constants.ExceptionCode;
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
import com.crewspace.api.dto.res.space.CreateSpaceResponseDTO;
import com.crewspace.api.exception.CustomException;
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
            .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_EMAIL_NOT_FOUND));

        SpaceMember spaceMember = createSpaceDTO.toSpaceMember(space, member, adminCategory);
        spaceMemberRepository.save(spaceMember);

        return CreateSpaceResponseDTO.toCreateSpaceResponseDTO(space);
    }
}
