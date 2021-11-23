package com.crewspace.api.dto.req.post;

import com.crewspace.api.domain.memberPost.FixedPost;
import com.crewspace.api.domain.memberPost.SavedPost;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long postId;

    private PostRequestDTO(Long spaceId, String memberEmail, Long postId) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.postId = postId;
    }

    public static PostRequestDTO of(Long spaceId, String memberEmail, Long postId){
        return new PostRequestDTO(spaceId, memberEmail, postId);
    }

    public SavedPost toSavedPost(Post post, SpaceMember spaceMember){
        return SavedPost.of(post, spaceMember);
    }

    public FixedPost toFixedPost(Post post, SpaceMember spaceMember){
        return FixedPost.of(post, spaceMember);
    }

}
