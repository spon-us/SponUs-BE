package com.sponus.sponusbe.auth.jwt.util;

import java.io.IOException;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorStatus;
import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.status.CommonErrorStatus;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil {

	private static final String JSON = "application/json;charset=UTF-8";

	public static void setResponse(HttpServletResponse response, TokenErrorStatus code) throws IOException {

		response.setContentType(JSON);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print(
			ApiResponse.onFailure(
				code.getCode(),
				code.getMessage(),
				null
			).toJsonString()
		);
	}

	public static void setResponse(HttpServletResponse response, CommonErrorStatus code) throws IOException {

		response.setContentType(JSON);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print(
			ApiResponse.onFailure(
				code.getCode(),
				code.getMessage(),
				null
			).toJsonString()
		);
	}

	public static void setResponse(HttpServletResponse response, JwtPair reissueToken) throws IOException {

		response.setContentType(JSON);
		response.setStatus(200);
		response.getWriter().print(
			ApiResponse.onSuccess(
				reissueToken
			).toJsonString()
		);
	}
}
