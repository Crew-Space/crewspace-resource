package com.crewspace.api.dto.req.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListRequestDTO {

    private Long postCategoryId;
    private int offset;
    private String type;
    private Long spaceId;
    private String memberEmail;

    private PostListRequestDTO(Long postCategoryId, int offset, String type, Long spaceId,
        String memberEmail) {
        this.postCategoryId = postCategoryId;
        this.offset = offset;
        this.type = type;
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
    }

    public static PostListRequestDTO of(Long postCategoryId, int offset, String type, Long spaceId,
        String memberEmail){
        return new PostListRequestDTO(postCategoryId, offset, type, spaceId, memberEmail);
    }
}
