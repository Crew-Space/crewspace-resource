package com.crewspace.api.dto.req.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryListRequestDTO {
    private Long spaceId;
    private String memberEmail;

    private CategoryListRequestDTO(Long spaceId, String memberEmail) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
    }

    public static CategoryListRequestDTO of(Long spaceId, String memberEmail){
        return new CategoryListRequestDTO(spaceId, memberEmail);
    }
}
