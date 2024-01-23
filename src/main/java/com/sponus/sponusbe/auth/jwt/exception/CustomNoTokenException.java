package com.sponus.sponusbe.auth.jwt.exception;

public class CustomNoTokenException extends RuntimeException {

	public CustomNoTokenException() {
		super(SecurityErrorCode.TOKEN_NOT_FOUND.getCode());
	}
}
