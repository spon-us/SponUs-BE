package com.sponus.sponusbe.domain.organization.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class OrganizationException extends CustomException {

	public OrganizationException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
