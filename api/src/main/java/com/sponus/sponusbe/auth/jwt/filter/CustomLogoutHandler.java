package com.sponus.sponusbe.auth.jwt.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			log.info("[*] Logout Filter");

			String accessToken = jwtUtil.resolveAccessToken(request);

			redisUtil.saveAsValue(
				accessToken,
				"logout",
				jwtUtil.getExpTime(accessToken),
				TimeUnit.MILLISECONDS
			);

			String email = jwtUtil.getEmail(accessToken);

			redisUtil.delete(
				email + "_refresh_token"
			);

			redisUtil.delete(
				email + "_fcm_token"
			);

		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken expired");
			throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED);
		}
	}
}