package com.sponus.sponusbe.domain.notification.dto.request;

import com.sponus.coredomain.domain.notification.Notification;

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
