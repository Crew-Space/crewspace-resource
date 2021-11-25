package com.crewspace.api.dto.req.spaceMember;

import com.crewspace.api.dto.req.category.CategoryListRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberListRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long memberCategoryId;

    private MemberListRequestDTO(Long spaceId, String memberEmail, Long memberCategoryId) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.memberCategoryId = memberCategoryId;
    }

    public static MemberListRequestDTO of(Long spaceId, String memberEmail, Long memberCategoryId){
        return new MemberListRequestDTO(spaceId, memberEmail, memberCategoryId);
    }

}
