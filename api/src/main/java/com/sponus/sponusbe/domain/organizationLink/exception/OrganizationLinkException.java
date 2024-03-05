package com.sponus.sponusbe.domain.organizationLink.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class OrganizationLinkException extends CustomException {
	public OrganizationLinkException(BaseErrorCode errorCode) {
		super(errorCode);
	}

}
