package com.sponus.sponusbe.domain.tag.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class TagException extends CustomException {
	public TagException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
