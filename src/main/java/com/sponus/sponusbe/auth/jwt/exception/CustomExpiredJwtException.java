package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorStatus;

public class CustomExpiredJwtException extends RuntimeException {

	public CustomExpiredJwtException() {
		super(TokenErrorStatus.TOKEN_EXPIRED.getCode());
	}
}
