package com.sponus.sponusbe.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.global.common.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

	private final JwtUtil jwtUtil;

	@GetMapping("/reissue")
	public ApiResponse<JwtPair> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		try {
			jwtUtil.validateRefreshToken(refreshToken);
			return ApiResponse.onSuccess(
				jwtUtil.reissueToken(refreshToken)
			);
		} catch (ExpiredJwtException eje) {
			throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED, eje);
		} catch (IllegalArgumentException iae) {
			throw new SecurityCustomException(SecurityErrorCode.INVALID_TOKEN, iae);
		}
	}
}
