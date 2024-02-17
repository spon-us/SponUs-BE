package com.sponus.sponusbe.domain.propose.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.propose.entity.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {

	Optional<Propose> findByImpUid(String impUid);

	Optional<Propose> findByProposingOrganizationIdAndAnnouncementId(Long proposingOrganizationId, Long announcementId);
}
