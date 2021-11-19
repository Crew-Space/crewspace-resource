package com.crewspace.api.domain.memberPost;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class SavedPost extends MemberPost {

    private SavedPost(Post post, SpaceMember member) {
        super(post, member);
    }

    public static SavedPost of(Post post, SpaceMember member){
        return new SavedPost(post, member);
    }
}
