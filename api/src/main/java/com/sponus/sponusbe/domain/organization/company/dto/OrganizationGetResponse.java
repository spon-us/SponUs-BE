package com.sponus.sponusbe.domain.organization.company.dto;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;

public record OrganizationGetResponse(
	Long id,
	String name,
	String email,
	String description,
	String imageUrl,
	int bookmarkCount,
	int viewCount,
	OrganizationType organizationType
) {
	public static OrganizationGetResponse of(Organization organization) {
		return new OrganizationGetResponse(
			organization.getId(),
			organization.getName(),
			organization.getEmail(),
			organization.getDescription(),
			organization.getImageUrl(),
			organization.getBookmarkCount(),
			organization.getViewCount(),
			organization.getOrganizationType()
		);
	}
}
