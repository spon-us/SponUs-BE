package com.sponus.sponusbe.domain.organization.company.dto;

import java.util.List;

import com.sponus.coredomain.domain.organization.company.Company;

public record CompanyGetResponse(
	Long id,
	String name,
	String email,
	String description,
	String imageUrl,
	int bookmarkCount,
	int viewCount,
	String organizationType,
	String profileStatus,
	String role,
	List<String> companyTypes,
	List<String> collaborationTypes,
	String sponsorshipContent
) {
	public static CompanyGetResponse of(Company company) {
		return new CompanyGetResponse(
			company.getId(),
			company.getName(),
			company.getEmail(),
			company.getDescription(),
			company.getImageUrl(),
			company.getBookmarkCount(),
			company.getViewCount(),
			company.getOrganizationType().name(),
			company.getProfileStatus().name(),
			company.getRole().name(),
			company.getSubTypeNames(),
			company.getCollaborationTypeNames(),
			company.getSponsorshipContent()
		);
	}
}
