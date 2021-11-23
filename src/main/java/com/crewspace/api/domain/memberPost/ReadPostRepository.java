package com.crewspace.api.domain.memberPost;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadPostRepository extends JpaRepository<ReadPost, Long> {
    Boolean existsByPostAndMember(Post post, SpaceMember spaceMember);
}
