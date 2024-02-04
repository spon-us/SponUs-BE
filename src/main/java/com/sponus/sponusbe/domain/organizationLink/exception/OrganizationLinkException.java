package com.sponus.sponusbe.domain.organizationLink.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class OrganizationLinkException extends CustomException {
	public OrganizationLinkException(BaseErrorCode errorCode) {
		super(errorCode);
	}

}
