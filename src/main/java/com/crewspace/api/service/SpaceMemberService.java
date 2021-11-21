package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.MEMBER_EMAIL_NOT_FOUND;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.spaceMember.EnteredSpaceRequestDTO;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponseDTO;
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
    public EnteredSpaceResponseDTO enteredSpaceList(EnteredSpaceRequestDTO enteredSpaceRequestDTO){

        Member member = memberRepository.findByEmail(enteredSpaceRequestDTO.getMemberEmail())
            .orElseThrow(() -> new CustomException(MEMBER_EMAIL_NOT_FOUND));

        List<SpaceMember> spaces = spaceMemberRepository.findByMember(member);
        return EnteredSpaceResponseDTO.toEnteredSpaceResponseDTO(spaces);
    }
}
