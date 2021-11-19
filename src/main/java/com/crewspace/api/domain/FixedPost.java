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
public class FixedPost extends MemberPost{
    @Builder
    public FixedPost(Post post, SpaceMember member) {
        super(post, member);
    }
}
