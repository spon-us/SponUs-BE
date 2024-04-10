package com.sponus.coredomain.domain.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sponus.coredomain.domain.organization.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findOrganizationByEmail(String email);

	List<Organization> findByNameContains(String name);
}
