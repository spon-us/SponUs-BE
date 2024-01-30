package com.sponus.sponusbe.domain.propose.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.propose.entity.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {
	// @Query("SELECT p FROM Propose p WHERE p.proposingOrganization.id = :id")
	// List<Propose> findSentPropose(Long id);
	//
	// @Query("SELECT p FROM Propose p WHERE p.proposedOrganization.id = :organizationId AND p.announcement.id = :announcementId")
	// List<Propose> findReceivedProposeWithAnnouncementId(Long organizationId, Long announcementId);

}
