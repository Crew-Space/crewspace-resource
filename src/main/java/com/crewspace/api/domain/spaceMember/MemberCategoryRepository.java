package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.space.Space;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {

    @Query("select m from MemberCategory m inner join fetch m.space where m.space.id = :spaceId and m.id = :id")
    Optional<MemberCategory> findByIdAndSpaceId(@Param("id") Long id, @Param("spaceId") Long spaceId);

    @Query("select m from MemberCategory m inner join fetch m.space where m.space.id = :spaceId")
    List<MemberCategory> findBySpaceId(@Param("spaceId") Long spaceId);

    @Query("select m from MemberCategory m where m.id in (:ids)")
    List<MemberCategory> findByIds(@Param("ids")List<Long> ids);

    List<MemberCategory> findBySpace(Space space);
}
