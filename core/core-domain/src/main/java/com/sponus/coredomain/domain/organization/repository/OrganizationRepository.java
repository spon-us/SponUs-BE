package com.sponus.coredomain.domain.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findOrganizationByEmail(String email);

	Boolean existsByName(String name);

	List<Organization> findByNameContains(String name);
}
