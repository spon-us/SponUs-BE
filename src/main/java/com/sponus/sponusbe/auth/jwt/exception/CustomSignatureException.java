package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorStatus;

public class CustomSignatureException extends RuntimeException {

	public CustomSignatureException() {
		super(TokenErrorStatus.SIGNATURE_ERROR.getCode());
	}
}
