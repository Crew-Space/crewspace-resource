package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long> {
    @Query("select m from SpaceMember m join fetch m.space ")
    List<SpaceMember> findByMember(Member member);
}
