package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.space.Space;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long> {
    List<SpaceMember> findByMember(Member member);
}
