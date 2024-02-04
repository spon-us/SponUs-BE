package com.sponus.sponusbe.domain.organizationLink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.organizationLink.entity.OrganizationLink;

public interface OrganizationLinkRepository extends JpaRepository<OrganizationLink, Long> {
}
