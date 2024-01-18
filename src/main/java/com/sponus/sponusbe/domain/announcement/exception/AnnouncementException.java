package com.sponus.sponusbe.domain.announcement.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class AnnouncementException extends CustomException {
	public AnnouncementException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
