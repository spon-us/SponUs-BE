package com.sponus.coredomain.domain.announcement.enums;

public enum AnnouncementStatus {
	OPENED, CLOSED;

	public static AnnouncementStatus of(String input) {
		return AnnouncementStatus.valueOf(input.toUpperCase());
	}
}
