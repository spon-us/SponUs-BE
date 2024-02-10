package com.sponus.sponusbe.domain.notification.dto.response;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.notification.entity.Notification;
import com.sponus.sponusbe.domain.propose.entity.Propose;

import lombok.Builder;

@Builder
public record NotificationSummaryResponse(
	Long id,
	String title,
	String body,
	Long organizationId,
	Long announcementId,
	Long proposeId,
	boolean isRead
) {
	public static NotificationSummaryResponse from(Notification notification) {
		Announcement announcement = notification.getAnnouncement();
		Propose propose = notification.getPropose();

		return NotificationSummaryResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.body(notification.getBody())
			.organizationId(notification.getOrganization().getId())
			.announcementId(announcement != null ? announcement.getId() : null)
			.proposeId(propose != null ? propose.getId() : null)
			.isRead(notification.isRead())
			.build();
	}
}
