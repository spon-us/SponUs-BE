package com.sponus.sponusbe.auth.jwt.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class SecurityCustomException extends CustomException {

	public SecurityCustomException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
