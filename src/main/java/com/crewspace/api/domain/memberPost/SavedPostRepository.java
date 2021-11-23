package com.crewspace.api.domain.memberPost;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {
    Optional<SavedPost> findByPostAndMember(Post post, SpaceMember spaceMember);
    List<SavedPost> deleteByPostAndMember(Post post, SpaceMember spaceMember);
    Boolean existsByPostAndMember(Post post, SpaceMember spaceMember);
}
