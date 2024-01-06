package com.sponus.sponusbe.auth.jwt.filter;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sponus.sponusbe.auth.jwt.exception.CustomExpiredJwtException;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private final RedisUtil redisUtil;

	private final JwtUtil jwtUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class.getName());

		try {
			logger.info("[*] Logout Filter");

			String accessToken = jwtUtil.resolveAccessToken(request);

			redisUtil.save(
				accessToken,
				"logout",
				jwtUtil.getExpTime(accessToken),
				TimeUnit.MILLISECONDS
			);

			redisUtil.delete(
				jwtUtil.getUsername(accessToken)
			);
		} catch (ExpiredJwtException e) {
			logger.warn("[*] case : accessToken expired");

			throw new CustomExpiredJwtException();
		}
	}
}
