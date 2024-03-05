package com.sponus.sponusbe.domain.announcement.dto.response;

import java.util.List;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;
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
	Long viewCount,
	boolean canApply // 공고 조회 시 지원 가능한지(처음 지원하는 것인지) 프론트에서 확인하기 위한 필드
) {
	public static AnnouncementDetailResponse from(Announcement announcement, boolean canApply) {
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
			.canApply(canApply)
			.build();
	}
}
