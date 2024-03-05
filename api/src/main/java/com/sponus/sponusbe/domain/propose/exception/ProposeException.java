package com.sponus.sponusbe.domain.propose.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class ProposeException extends CustomException {

	public ProposeException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
