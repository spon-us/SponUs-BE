package com.sponus.sponusbe.domain.announcement.entity.enums;

import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;

public enum AnnouncementStatus {
	OPENED, CLOSED;

	public static AnnouncementStatus of(String input) {
		try {
			return AnnouncementStatus.valueOf(input.toUpperCase());
		} catch (Exception e) {
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ANNOUNCEMENT_STATUS);
		}
	}
}
