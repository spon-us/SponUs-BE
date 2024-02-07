package com.sponus.sponusbe.domain.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findOrganizationByEmail(String email);

	List<Organization> findByNameContains(String name);
}
