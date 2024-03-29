package com.sponus.sponusbe.domain.notification.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.report.Report;

import lombok.Builder;

@Builder
public record NotificationSummaryResponse(
	Long id,
	String title,
	String body,
	Long organizationId,
	Long announcementId,
	Long proposeId,
	Long reportId,
	boolean isRead,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static NotificationSummaryResponse from(Notification notification) {
		Announcement announcement = notification.getAnnouncement();
		Propose propose = notification.getPropose();
		Report report = notification.getReport();

		return NotificationSummaryResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.body(notification.getBody())
			.organizationId(notification.getOrganization().getId())
			.announcementId(announcement != null ? announcement.getId() : null)
			.proposeId(propose != null ? propose.getId() : null)
			.reportId(report != null ? report.getId() : null)
			.isRead(notification.isRead())
			.createdAt(notification.getCreatedAt())
			.updatedAt(notification.getUpdatedAt())
			.build();
	}
}
