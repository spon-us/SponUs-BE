package com.sponus.sponusbe.domain.notification.dto.response;

import com.sponus.sponusbe.domain.notification.entity.Notification;

import lombok.Builder;

@Builder
public record NotificationCreateResponse(
	String title,
	String body,
	Long organizationId,
	Long announcementId,
	Long proposeId
) {
	public static NotificationCreateResponse from(Notification notification) {
		return NotificationCreateResponse.builder()
			.title(notification.getTitle())
			.body(notification.getBody())
			.organizationId(notification.getOrganization().getId())
			.announcementId(notification.getAnnouncement().getId())
			.proposeId(notification.getPropose().getId())
			.build();
	}
}
