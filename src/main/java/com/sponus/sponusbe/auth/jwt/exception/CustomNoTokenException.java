package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorStatus;

public class CustomNoTokenException extends RuntimeException {

	public CustomNoTokenException() {
		super(TokenErrorStatus.NO_TOKEN.getCode());
	}
}
