package com.sponus.sponusbe.auth.controller;

import static com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

	private final JwtUtil jwtUtil;

	@GetMapping("/reissue")
	public ApiResponse<JwtPair> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		try {
			jwtUtil.validateRefreshToken(refreshToken);
			log.info("[*] Valid RefreshToken");
			return ApiResponse.onSuccess(
				jwtUtil.reissueToken(refreshToken)
			);
		} catch (IllegalArgumentException iae) {
			throw new SecurityCustomException(INVALID_TOKEN, iae);
		} catch (ExpiredJwtException eje) {
			throw new SecurityCustomException(TOKEN_EXPIRED, eje);
		}
	}
}
