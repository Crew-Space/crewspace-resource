package com.crewspace.api.domain.post;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CommunityPost extends Post {
    @Builder
    public CommunityPost(SpaceMember author, PostCategory postCategory, String description) {
        super(author, postCategory, description);
    }
}
