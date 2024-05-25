package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.coredomain.domain.organization.Organization;

public record OrganizationSearchResponse(
	Long id,
	String name,
	String imageUrl
) {
	public static OrganizationSearchResponse of(Organization organization) {
		return new OrganizationSearchResponse(
			organization.getId(),
			organization.getName(),
			organization.getImageUrl()
		);
	}
}
