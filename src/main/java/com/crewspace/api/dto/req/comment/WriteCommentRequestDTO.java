package com.crewspace.api.dto.req.comment;

import com.crewspace.api.domain.comment.Comment;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteCommentRequestDTO {
    private String memberEmail;
    private Long spaceId;
    private Long postId;
    private String description;

    public Comment toComment(SpaceMember commentAuthor, Post post, String description){
        return Comment.of(commentAuthor,post, description);
    }
}
