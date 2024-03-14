package com.sponus.coreinfrafirebase;

import com.sponus.coredomain.domain.notification.Notification;

import lombok.Builder;

@Builder
public record FcmMessage(
	boolean validateOnly,
	Message message
) {
	public static FcmMessage of(boolean validateOnly, Message message) {
		return FcmMessage.builder()
			.validateOnly(validateOnly)
			.message(message)
			.build();
	}

	@Builder
	public record Message(
		String token,
		NotificationSummary notification
	) {
		public static Message of(String token, NotificationSummary notificationSummary) {
			return Message.builder()
				.token(token)
				.notification(notificationSummary)
				.build();
		}
	}

	@Builder
	public record NotificationSummary(
		String title,
		String body,
		String image
	) {
		public static NotificationSummary from(Notification notification) {
			return NotificationSummary.builder()
				.title(notification.getTitle())
				.body(notification.getBody())
				.image(null)
				.build();
		}
	}
}
