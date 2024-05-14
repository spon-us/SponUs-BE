package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.OrganizationLink;

public interface OrganizationLinkRepository extends JpaRepository<OrganizationLink, Long> {
}
