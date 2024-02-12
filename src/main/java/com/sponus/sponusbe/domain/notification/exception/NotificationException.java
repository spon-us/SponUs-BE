package com.sponus.sponusbe.domain.notification.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class NotificationException extends CustomException {

	public NotificationException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
