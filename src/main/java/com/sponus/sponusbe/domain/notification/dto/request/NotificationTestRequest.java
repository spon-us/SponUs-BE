package com.sponus.sponusbe.domain.notification.dto.request;

import com.sponus.sponusbe.domain.notification.entity.Notification;

public record NotificationTestRequest(
	String title,
	String body
) {
	public Notification toEntity() {
		return Notification.builder()
			.title(title)
			.body(body)
			.build();
	}
}
