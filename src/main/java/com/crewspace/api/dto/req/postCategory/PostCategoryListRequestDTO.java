package com.crewspace.api.dto.req.postCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCategoryListRequestDTO {
    private Long spaceId;
    private String memberEmail;

    private PostCategoryListRequestDTO(Long spaceId, String memberEmail) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
    }

    public static PostCategoryListRequestDTO of(Long spaceId, String memberEmail){
        return new PostCategoryListRequestDTO(spaceId, memberEmail);
    }
}
