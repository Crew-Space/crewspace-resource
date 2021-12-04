package com.crewspace.api.dto.req.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListRequestDTO {
    private int offset;
    private String memberEmail;
    private Long spaceId;
    private Long postId;

    private CommentListRequestDTO(int offset, String memberEmail, Long spaceId, Long postId) {
        this.offset = offset;
        this.memberEmail = memberEmail;
        this.spaceId = spaceId;
        this.postId = postId;
    }

    public static CommentListRequestDTO of(int offset, String memberEmail, Long spaceId, Long postId){
        return new CommentListRequestDTO(offset, memberEmail, spaceId, postId);
    }
}
