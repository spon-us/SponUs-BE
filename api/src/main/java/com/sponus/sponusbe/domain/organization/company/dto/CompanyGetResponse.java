package com.sponus.sponusbe.domain.organization.company.dto;

import com.sponus.coredomain.domain.organization.Company;

import lombok.Builder;

@Builder
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
	String companyType,
	String collaborationType,
	String sponsorshipContent
) {
	public static CompanyGetResponse of(Company company) {
		return CompanyGetResponse.builder()
			.id(company.getId())
			.name(company.getName())
			.email(company.getEmail())
			.description(company.getDescription())
			.imageUrl(company.getImageUrl())
			.bookmarkCount(company.getBookmarkCount())
			.viewCount(company.getViewCount())
			.organizationType(company.getOrganizationType().name())
			.profileStatus(company.getProfileStatus().name())
			.role(company.getRole().name())
			.companyType(company.getCompanyType().name())
			.collaborationType(company.getCollaborationType().name())
			.sponsorshipContent(company.getSponsorshipContent())
			.build();
	}
}
