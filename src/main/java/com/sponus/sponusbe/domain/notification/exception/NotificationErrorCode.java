package com.sponus.sponusbe.domain.notification.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode implements BaseErrorCode {
	NOTIFICATION_ERROR(HttpStatus.BAD_REQUEST, "NOTI4000", "알림 관련 에러"),
	NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTI4001", "존재하지 않는 알림입니다."),
	INVALID_ORGANIZATION(HttpStatus.BAD_REQUEST, "NOTI4002", "해당 단체의 알림이 아닙니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
