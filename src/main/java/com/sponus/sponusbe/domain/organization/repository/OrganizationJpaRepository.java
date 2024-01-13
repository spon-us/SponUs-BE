package com.sponus.sponusbe.domain.organization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.organization.entity.Organization;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findOrganizationByEmail(String email);
}
