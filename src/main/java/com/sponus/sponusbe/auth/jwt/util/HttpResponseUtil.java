package com.sponus.sponusbe.auth.jwt.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void setSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		String responseBody = objectMapper.writeValueAsString(ApiResponse.onSuccess(body));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}

	public static void setErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getOutputStream(), body);
	}
}
