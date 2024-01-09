package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorCode;

public class CustomNoTokenException extends RuntimeException {

	public CustomNoTokenException() {
		super(TokenErrorCode.NO_TOKEN.getCode());
	}
}
