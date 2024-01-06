package com.sponus.sponusbe.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sponus.sponusbe.global.common.status.SuccessStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "statusCode", "message", "content"})
public class ApiResponse<T> {

	@JsonProperty("isSuccess")
	@NonNull
	private final Boolean isSuccess;

	@JsonProperty("statusCode")
	@NonNull
	private final String statusCode;

	@JsonProperty("message")
	@NonNull
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("content")
	private T content;

	// 성공한 경우 응답 생성
	public static <T> ApiResponse<T> onSuccess(T content) {
		return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), content);
	}

	// 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String statusCode, String message, T content) {
		return new ApiResponse<>(false, statusCode, message, content);
	}

	// Json serialize
	public String toJsonString() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}
}
