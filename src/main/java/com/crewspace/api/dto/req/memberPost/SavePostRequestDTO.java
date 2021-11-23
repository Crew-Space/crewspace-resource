package com.crewspace.api.dto.req.memberPost;

import com.crewspace.api.domain.memberPost.SavedPost;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavePostRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long postId;

    private SavePostRequestDTO(Long spaceId, String memberEmail, Long postId) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.postId = postId;
    }

    public static SavePostRequestDTO of(Long spaceId, String memberEmail, Long postId){
        return new SavePostRequestDTO(spaceId, memberEmail, postId);
    }

    public SavedPost toSavedPost(Post post, SpaceMember spaceMember){
        return SavedPost.of(post, spaceMember);
    }

}
