package com.sponus.sponusbe.domain.notification.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class NotificationException extends CustomException {

	public NotificationException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
