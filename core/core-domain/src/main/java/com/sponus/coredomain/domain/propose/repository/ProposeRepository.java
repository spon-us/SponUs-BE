package com.sponus.coredomain.domain.propose.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {

	Boolean existsByOrganization(Organization organization);
	Optional<Propose> findByOrganization(Organization organization);
}
