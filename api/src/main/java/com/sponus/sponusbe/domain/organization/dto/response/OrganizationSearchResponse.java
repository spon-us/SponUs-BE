package com.sponus.sponusbe.domain.organization.dto.response;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;

import lombok.Builder;

@Builder
public record OrganizationSearchResponse(
	Long id,
	String name,
	String imageUrl,
	OrganizationType organizationType,
	boolean isBookmarked
) {
	public static OrganizationSearchResponse of(Organization organization, boolean isBookmarked) {
		return OrganizationSearchResponse.builder()
			.id(organization.getId())
			.name(organization.getName())
			.imageUrl(organization.getImageUrl())
			.organizationType(organization.getOrganizationType())
			.isBookmarked(isBookmarked)
			.build();
	}
}
