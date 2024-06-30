package com.sponus.coredomain.domain.propose.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {

	Boolean existsByOrganization(Organization organization);

	// Optional<Propose> findByOrganization(Organization organization);

	@Query("SELECT COUNT(p) FROM Propose p WHERE p.organization = :organization AND p.createdAt >= :startOfDay")
	Long countProposesByOrganizationToday(@Param("organization") Organization organization, @Param("startOfDay") LocalDateTime startOfDay);

	List<Propose> findByOrganizationOrderByCreatedAtDesc(Organization organization);
}
