package com.crewspace.api.domain.memberPost;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedPostRepository  extends JpaRepository<FixedPost, Long> {
    Optional<FixedPost> findByPostAndMember(Post post, SpaceMember spaceMember);
}
