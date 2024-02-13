package com.sponus.sponusbe.domain.announcement.dto.response;

import java.util.List;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSummaryResponse;

import lombok.Builder;

@Builder
public record AnnouncementDetailResponse(
	Long id,
	OrganizationSummaryResponse writer,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	String content,
	List<AnnouncementImageResponse> announcementImages,
	AnnouncementStatus status,
	Long viewCount
) {
	public static AnnouncementDetailResponse from(Announcement announcement) {
		List<AnnouncementImageResponse> announcementImages = announcement.getAnnouncementImages()
			.stream()
			.map(AnnouncementImageResponse::from)
			.toList();

		OrganizationSummaryResponse writer = OrganizationSummaryResponse.from(announcement.getWriter());

		return AnnouncementDetailResponse.builder()
			.id(announcement.getId())
			.writer(writer)
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.content(announcement.getContent())
			.announcementImages(announcementImages)
			.status(announcement.getStatus())
			.viewCount(announcement.getViewCount())
			.build();
	}
}
