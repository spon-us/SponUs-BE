package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorCode;

public class CustomExpiredJwtException extends RuntimeException {

	public CustomExpiredJwtException() {
		super(TokenErrorCode.TOKEN_EXPIRED.getCode());
	}
}
