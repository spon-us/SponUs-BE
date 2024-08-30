package com.sponus.sponusbe.domain.notification.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

import lombok.Builder;

@Builder
public record NotificationSummaryResponse(
	Long id,
	String title,
	String body,
	String organizationProfile,
	String organizationName,
	Long proposeId,
	boolean isRead,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static NotificationSummaryResponse from(Notification notification) {
		Propose propose = notification.getPropose();
		Organization organization = notification.getOrganization();
		String organizationProfile = organization.getImageUrl() != null ? organization.getImageUrl() : null;

		return NotificationSummaryResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.body(notification.getBody())
			.organizationName(notification.getOrganization().getName())
			.organizationProfile(organizationProfile)
			.proposeId(propose != null ? propose.getId() : null)
			.isRead(notification.isRead())
			.createdAt(notification.getCreatedAt())
			.updatedAt(notification.getUpdatedAt())
			.build();
	}
}
