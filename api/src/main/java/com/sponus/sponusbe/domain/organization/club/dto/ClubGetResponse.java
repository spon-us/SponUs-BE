package com.sponus.sponusbe.domain.organization.club.dto;

import com.sponus.coredomain.domain.organization.Club;

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
	String clubType
) {
	public static ClubGetResponse of(Club club) {
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
			club.getClubType().name()
		);
	}
}
