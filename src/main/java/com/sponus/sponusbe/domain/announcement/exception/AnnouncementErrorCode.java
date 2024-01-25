package com.sponus.sponusbe.domain.announcement.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnnouncementErrorCode implements BaseErrorCode {
	ANNOUNCEMENT_ERROR(HttpStatus.BAD_REQUEST, "4000", "공지사항 에러입니다.");

	private HttpStatus httpStatus;
	private String code;
	private String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return null;
	}
}
