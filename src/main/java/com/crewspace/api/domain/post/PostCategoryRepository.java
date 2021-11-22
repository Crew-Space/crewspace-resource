package com.crewspace.api.domain.post;

import com.crewspace.api.domain.space.Space;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findBySpace(Space space);
}
