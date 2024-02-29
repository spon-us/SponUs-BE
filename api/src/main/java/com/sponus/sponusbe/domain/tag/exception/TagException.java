package com.sponus.sponusbe.domain.tag.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class TagException extends CustomException {
	public TagException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
