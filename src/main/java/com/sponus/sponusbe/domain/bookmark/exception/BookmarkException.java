package com.sponus.sponusbe.domain.bookmark.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class BookmarkException extends CustomException {
	public BookmarkException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
