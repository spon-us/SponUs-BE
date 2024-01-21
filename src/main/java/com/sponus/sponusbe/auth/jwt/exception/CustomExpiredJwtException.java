package com.sponus.sponusbe.auth.jwt.exception;

public class CustomExpiredJwtException extends RuntimeException {

	public CustomExpiredJwtException() {
		super(SecurityErrorCode.TOKEN_EXPIRED.getCode());
	}
}
