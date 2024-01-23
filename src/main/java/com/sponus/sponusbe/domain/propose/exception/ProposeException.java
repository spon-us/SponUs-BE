package com.sponus.sponusbe.domain.propose.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class ProposeException extends CustomException {

	public ProposeException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
