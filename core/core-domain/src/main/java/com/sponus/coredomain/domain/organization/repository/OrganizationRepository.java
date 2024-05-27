package com.sponus.coredomain.domain.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sponus.coredomain.domain.organization.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findOrganizationByEmail(String email);

	@Query("SELECT o FROM Organization o WHERE o.organizationType = :organizationType")
	Page<Organization> findOrganizations(String organizationType, Pageable pageable);

	@Query("SELECT COUNT(o) FROM Organization o WHERE o.organizationType = :organizationType")
	Long countByOrganizationType(String organizationType);

	Boolean existsByName(String name);

	List<Organization> findByNameContains(String name, Pageable pageable);

	@Query("SELECT COUNT(o) FROM Organization o WHERE o.name LIKE '%:keyword%'")
	Long countByNameContains(String organizationType);

	@Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organization o WHERE o.email = :email")
	Boolean checkDuplicateEmail(String email);
}
