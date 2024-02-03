package com.sponus.sponusbe.domain.announcement.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.sponus.sponusbe.domain.announcement.entity.AnnouncementImage;

import lombok.Builder;

@Builder
public record AnnouncementImageResponse(
	Long id,
	String name,
	String url
) {

	public static List<AnnouncementImageResponse> announcementImage(List<AnnouncementImage> announcementImages) {
		return announcementImages.stream()
			.map(imageUrl -> AnnouncementImageResponse.builder()
				.id(imageUrl.getId())
				.name(imageUrl.getName())
				.url(imageUrl.getUrl())
				.build())
			.collect(Collectors.toList());
	}
}
