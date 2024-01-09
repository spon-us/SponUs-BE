package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorCode;

public class CustomSignatureException extends RuntimeException {

	public CustomSignatureException() {
		super(TokenErrorCode.SIGNATURE_ERROR.getCode());
	}
}
