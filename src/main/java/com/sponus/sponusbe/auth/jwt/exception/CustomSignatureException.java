package com.sponus.sponusbe.auth.jwt.exception;

public class CustomSignatureException extends RuntimeException {

	public CustomSignatureException() {
		super(SecurityErrorCode.SIGNATURE_ERROR.getCode());
	}
}
