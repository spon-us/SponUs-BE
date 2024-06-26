package com.sponus.coredomain.domain.propose.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {

	Boolean existsByOrganization(Organization organization);

	// Optional<Propose> findByOrganization(Organization organization);

	@Query("SELECT COUNT(p) FROM Propose p WHERE p.organization = :organization AND p.createdAt >= :startOfDay")
	Long countProposesByOrganizationToday(@Param("organization") Organization organization,
		@Param("startOfDay") LocalDateTime startOfDay);

	Page<Propose> findByOrganizationOrderByCreatedAtDesc(Organization organization, Pageable pageable);

	Long countByOrganization(Organization organization);

	Page<Propose> findByTargetOrderByCreatedAtDesc(Organization target, Pageable pageable);

	Long countByTarget(Organization target);
}
