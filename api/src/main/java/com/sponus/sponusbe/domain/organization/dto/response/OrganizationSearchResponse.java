package com.sponus.sponusbe.domain.organization.dto.response;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;

public record OrganizationSearchResponse(
	Long id,
	String name,
	String imageUrl,
	OrganizationType organizationType
) {
	public static OrganizationSearchResponse of(Organization organization) {
		return new OrganizationSearchResponse(
			organization.getId(),
			organization.getName(),
			organization.getImageUrl(),
			organization.getOrganizationType()
		);
	}
}
