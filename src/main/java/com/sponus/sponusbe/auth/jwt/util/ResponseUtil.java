package com.sponus.sponusbe.auth.jwt.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {

	private static final String JSON = "application/json;charset=UTF-8";

	public static void setSuccessResponse(HttpServletResponse response, HttpStatus code, Object body) throws IOException {

		response.setContentType(JSON);
		response.setStatus(code.value());
		response.getWriter().print(
			ApiResponse.onFailure(
				code.name(),
				code.getReasonPhrase(),
				body
			).toJsonString()
		);
	}

	public static void setErrorResponse(HttpServletResponse response, HttpStatus code) throws IOException {

		response.setContentType(JSON);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print(
			ApiResponse.onFailure(
				code.name(),
				code.getReasonPhrase(),
				null
			).toJsonString()
		);
	}
}
