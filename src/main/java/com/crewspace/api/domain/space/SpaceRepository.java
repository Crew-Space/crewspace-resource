package com.crewspace.api.domain.space;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByInvitationCode(String invitationCode);
}
