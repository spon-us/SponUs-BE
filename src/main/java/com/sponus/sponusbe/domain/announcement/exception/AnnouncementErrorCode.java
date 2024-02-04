package com.sponus.sponusbe.domain.announcement.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnnouncementErrorCode implements BaseErrorCode {
	ANNOUNCEMENT_ERROR(HttpStatus.BAD_REQUEST, "ANC4000", "공고 관련 에러"),
	ANNOUNCEMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "ANC4001", "이미 삭제된 공지사항입니다."),
	INVALID_ORGANIZATION(HttpStatus.BAD_REQUEST, "ANC4002", "해당 단체의 공고가 아닙니다."),
	INVALID_ANNOUNCEMENT_STATUS(HttpStatus.BAD_REQUEST, "ANC4003", "해당 상태의 공고는 수정할 수 없습니다."),
	ANNOUNCEMENT_NOT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "ANC4004", "진행 중인 공고가 아닙니다."),
	ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANC4040", "해당 공고가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
