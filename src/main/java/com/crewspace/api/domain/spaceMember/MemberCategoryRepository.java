package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.space.Space;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
    List<MemberCategory>  findBySpace(Space space);
}
