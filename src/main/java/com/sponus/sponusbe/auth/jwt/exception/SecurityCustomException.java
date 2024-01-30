package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class SecurityCustomException extends CustomException {

	//임시
	private final Throwable cause;

	public SecurityCustomException(BaseErrorCode errorCode) {
		super(errorCode);
		this.cause = null;
	}

	public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
		super(errorCode);
		this.cause = cause;
	}
}
