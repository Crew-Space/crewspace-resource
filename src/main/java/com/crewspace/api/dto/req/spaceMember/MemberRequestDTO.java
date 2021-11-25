package com.crewspace.api.dto.req.spaceMember;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long memberId;

    private MemberRequestDTO(Long spaceId, String memberEmail, Long memberId) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.memberId = memberId;
    }

    public static MemberRequestDTO of(Long spaceId, String memberEmail, Long memberId){
        return new MemberRequestDTO(spaceId, memberEmail, memberId);
    }
}
