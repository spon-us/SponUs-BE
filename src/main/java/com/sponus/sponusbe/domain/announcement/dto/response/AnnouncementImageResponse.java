package com.sponus.sponusbe.domain.announcement.dto.response;

import com.sponus.sponusbe.domain.announcement.entity.AnnouncementImage;

import lombok.Builder;

@Builder
public record AnnouncementImageResponse(
	Long id,
	String name,
	String url
) {
	public static AnnouncementImageResponse from(AnnouncementImage image) {
		return AnnouncementImageResponse.builder()
			.id(image.getId())
			.name(image.getName())
			.url(image.getUrl())
			.build();
	}
}
