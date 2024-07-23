package com.sponus.sponusbe.domain.organization.club.dto;

import java.util.List;

import com.sponus.coredomain.domain.organization.club.Club;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkGetResponse;

public record ClubGetResponse(
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
	int memberCount,
	List<OrganizationLinkGetResponse> links,
	List<String> clubTypes
) {
	public static ClubGetResponse of(Club club) {
		final List<OrganizationLinkGetResponse> links = club.getOrganizationLinks()
			.stream()
			.map(OrganizationLinkGetResponse::from)
			.toList();
		return new ClubGetResponse(
			club.getId(),
			club.getName(),
			club.getEmail(),
			club.getDescription(),
			club.getImageUrl(),
			club.getBookmarkCount(),
			club.getViewCount(),
			club.getOrganizationType().name(),
			club.getProfileStatus().name(),
			club.getRole().name(),
			club.getMemberCount(),
			links,
			club.getSubTypeNames()
		);
	}
}
