package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.MEMBER_EMAIL_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.MEMBER_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_OR_MEMBER_CATEGORY_NOT_FOUND;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.MemberCategoryRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.spaceMember.EnteredSpaceRequestDTO;
import com.crewspace.api.dto.req.spaceMember.MemberListRequestDTO;
import com.crewspace.api.dto.req.spaceMember.MemberRequestDTO;
import com.crewspace.api.dto.req.spaceMember.MemberSearchRequestDTO;
import com.crewspace.api.dto.req.spaceMember.ModifyMemberRequestDTO;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponseDTO;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberListResponseDTO;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberResponseDTO;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberSearchResponseDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpaceMemberService {
    private final MemberRepository memberRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    public EnteredSpaceResponseDTO enteredSpaceList(EnteredSpaceRequestDTO enteredSpaceRequestDTO){

        Member member = memberRepository.findByEmail(enteredSpaceRequestDTO.getMemberEmail())
            .orElseThrow(() -> new CustomException(MEMBER_EMAIL_NOT_FOUND));

        List<SpaceMember> spaces = spaceMemberRepository.findByMember(member);
        return EnteredSpaceResponseDTO.toEnteredSpaceResponseDTO(spaces);
    }

    public SpaceMemberListResponseDTO memberList(MemberListRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        List<SpaceMember> spaceMembers;
        if(request.getMemberCategoryId().equals(Long.valueOf(-1))){
            spaceMembers = spaceMemberRepository.findBySpaceOrderByName(spaceMember.getSpace());
        }else{
            spaceMembers = spaceMemberRepository.findBySpaceAndMemberCategoryIdOrderByName(spaceMember.getSpace(), request.getMemberCategoryId());
        }

        return SpaceMemberListResponseDTO.from(spaceMembers);
    }

    public SpaceMemberSearchResponseDTO memberSearch(MemberSearchRequestDTO request){

        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        List<SpaceMember> spaceMembers = spaceMemberRepository.findBySpaceAndMemberName(
            spaceMember.getSpace(), request.getKeyword());

        return SpaceMemberSearchResponseDTO.from(spaceMembers);
    }

    public SpaceMemberResponseDTO memberInfo(MemberRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        SpaceMember member = spaceMemberRepository.findBySpaceAndMemberId(
                spaceMember.getSpace(), request.getMemberId())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        return SpaceMemberResponseDTO.from(member);
    }

    @Transactional
    public void modifyMember(ModifyMemberRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        MemberCategory memberCategory = memberCategoryRepository.findByIdAndSpaceId(
                request.getMemberCategoryId(), request.getSpaceId())
            .orElseThrow(() -> new CustomException(SPACE_OR_MEMBER_CATEGORY_NOT_FOUND));

        spaceMember.update(request, memberCategory);
    }
}
