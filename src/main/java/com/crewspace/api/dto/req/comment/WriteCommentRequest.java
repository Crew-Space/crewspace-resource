package com.crewspace.api.dto.req.comment;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WriteCommentRequest {
    @NotNull(message = "게시글 id는 필수입니다!")
    private Long postId;
    @NotNull(message = "댓글 내용은 필수입니다!")
    private String description;

    public WriteCommentRequestDTO toWriteCommentRequestDTO(Long spaceId, String memberEmail){
        return WriteCommentRequestDTO.builder()
            .spaceId(spaceId)
            .memberEmail(memberEmail)
            .postId(postId)
            .description(description)
            .build();
    }
}
