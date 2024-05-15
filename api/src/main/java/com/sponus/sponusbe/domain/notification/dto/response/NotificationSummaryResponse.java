package com.sponus.sponusbe.domain.notification.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.propose.Propose;

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
		Propose propose = notification.getPropose();

		return NotificationSummaryResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.body(notification.getBody())
			.organizationId(notification.getOrganization().getId())
			.proposeId(propose != null ? propose.getId() : null)
			.isRead(notification.isRead())
			.createdAt(notification.getCreatedAt())
			.updatedAt(notification.getUpdatedAt())
			.build();
	}
}
