package com.sponus.sponusbe.domain.organization.company.dto;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;

import lombok.Builder;

@Builder
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
		return OrganizationGetResponse.builder()
			.id(organization.getId())
			.name(organization.getName())
			.email(organization.getEmail())
			.description(organization.getDescription())
			.imageUrl(organization.getImageUrl())
			.bookmarkCount(organization.getBookmarkCount())
			.viewCount(organization.getViewCount())
			.organizationType(organization.getOrganizationType())
			.build();
	}
}
