package com.sponus.sponusbe.domain.organization.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class OrganizationException extends CustomException {

	public OrganizationException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
