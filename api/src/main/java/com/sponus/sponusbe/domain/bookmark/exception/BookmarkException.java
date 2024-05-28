package com.sponus.sponusbe.domain.bookmark.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class BookmarkException extends CustomException {

	public BookmarkException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
