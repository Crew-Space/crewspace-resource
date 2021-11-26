package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.space.Space;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long> {
    @Query("select m from SpaceMember m join fetch m.space where m.member = :member")
    List<SpaceMember> findByMember(@Param("member") Member member);
    Boolean existsBySpace_IdAndMember(Long spaceId, Member member);

    @Query("select m from SpaceMember m join fetch m.space where m.space.id = :spaceId and m.member.email = :memberEmail")
    Optional<SpaceMember> findBySpaceIdAndMemberEmail(@Param("spaceId") Long spaceI, @Param("memberEmail") String memberEmail);

    long countBySpace(Space space);

    @Query("select m from SpaceMember  m join fetch m.memberCategory where m.space = :space order by m.name")
    List<SpaceMember> findBySpaceOrderByName(@Param("space") Space space);

    @Query("select m from SpaceMember  m join fetch m.memberCategory where m.space = :space and m.memberCategory.id = :memberCategoryId order by m.name")
    List<SpaceMember> findBySpaceAndMemberCategoryIdOrderByName(@Param("space") Space space, @Param("memberCategoryId") Long memberCategoryId);

    @Query("select m from SpaceMember m join fetch m.memberCategory where m.space = :space and m.name like concat('%', :memberName, '%') order by m.name")
    List<SpaceMember> findBySpaceAndMemberName(@Param("space") Space space, @Param("memberName") String memberName);

    @Query("select m from SpaceMember m join fetch m.memberCategory where m.space = :space and m.id = :memberId")
    Optional<SpaceMember> findBySpaceAndMemberId(@Param("space") Space space, @Param("memberId") Long memberId);
}