package com.sponus.sponusbe.domain.announcement.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class AnnouncementException extends CustomException {
	public AnnouncementException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
