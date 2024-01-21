package com.sponus.sponusbe.auth.jwt.exception;

public class CustomNoTokenException extends RuntimeException {

	public CustomNoTokenException() {
		super(SecurityErrorCode.NO_TOKEN.getCode());
	}
}
