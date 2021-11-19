package com.crewspace.api.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CommunityPost extends Post{
    @Builder
    public CommunityPost(SpaceMember author, PostCategory postCategory, String description) {
        super(author, postCategory, description);
    }
}
