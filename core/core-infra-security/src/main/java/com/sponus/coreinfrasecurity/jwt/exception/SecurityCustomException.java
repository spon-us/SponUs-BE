package com.sponus.coreinfrasecurity.jwt.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.Getter;

@Getter
public class SecurityCustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	private final Throwable cause;

	public SecurityCustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
		this.cause = null;
	}

	public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
		this.errorCode = errorCode;
		this.cause = cause;
	}
}
