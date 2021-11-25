package com.crewspace.api.dto.req.spaceMember;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSearchRequestDTO {
    private Long spaceId;
    private String memberEmail;
    private String keyword;

    private MemberSearchRequestDTO(Long spaceId, String memberEmail, String keyword) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.keyword = keyword;
    }

    public static  MemberSearchRequestDTO of(Long spaceId, String memberEmail, String keyword){
        return new MemberSearchRequestDTO(spaceId, memberEmail, keyword);
    }
}
