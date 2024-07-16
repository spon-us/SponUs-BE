package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.conditions.OrganizationSearchCondition;

public interface OrganizationRepositoryCustom {

	Page<Organization> searchOrganizationV2(OrganizationSearchCondition condition, Pageable pageable);
}
