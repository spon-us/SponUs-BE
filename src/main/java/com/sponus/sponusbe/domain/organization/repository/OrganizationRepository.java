package com.sponus.sponusbe.domain.organization.repository;

import com.sponus.sponusbe.domain.organization.entity.Organization;

public interface OrganizationRepository {

	Organization save(Organization organization);

	Organization findOrganizationByEmail(String email);
}
