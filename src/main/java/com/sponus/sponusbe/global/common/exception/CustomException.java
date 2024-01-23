package com.sponus.sponusbe.global.common.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
