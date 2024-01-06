package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.util.ResponseUtil.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.sponusbe.auth.jwt.dto.CachedHttpServletRequest;
import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.CustomExpiredJwtException;
import com.sponus.sponusbe.auth.jwt.exception.CustomNoTokenException;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;
import com.sponus.sponusbe.auth.user.CustomUserDetails;
import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.enums.Role;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException, CustomNoTokenException, MalformedJwtException {

		logger.info("[*] Jwt Filter");

		CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
		try {
			String accessToken = jwtUtil.resolveAccessToken(cachedHttpServletRequest);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(cachedHttpServletRequest, response);

				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null &&
				redisUtil.get(accessToken).equals("logout")) {
				logger.info("[*] Logout accessToken");

				filterChain.doFilter(cachedHttpServletRequest, response);

				return;
			}

			logger.info("[*] Authorization with Token");

			authenticateAccessToken(accessToken);

			filterChain.doFilter(cachedHttpServletRequest, response);

		} catch (ExpiredJwtException e) {
			logger.warn("[*] case : accessToken Expired");

			// accessToken 만료 시 Body에 있는 refreshToken 확인

			String refreshToken = request.getHeader("refreshToken");

			logger.info("[*] refreshToken : " + refreshToken);
			try {
				if (jwtUtil.validateRefreshToken(refreshToken)) {

					logger.info("[*] case : accessToken Expired && refreshToken in redis");

					// refreshToken 유효 시 재발급
					JwtPair reissueTokens = jwtUtil.reissueToken(refreshToken);

					setResponse(response, reissueTokens);
				}
			} catch (ExpiredJwtException e1) {
				logger.info("[*] case : accessToken, refreshToken expired");
				throw new CustomExpiredJwtException();
			} catch (IllegalArgumentException e2) {
				logger.info("[*] case : refreshToken expired");
				throw new CustomNoTokenException();
			}
		}
	}

	private void authenticateAccessToken(String accessToken) {

		// 토큰에서 username과 role 획득
		String username = jwtUtil.getUsername(accessToken);
		String role = jwtUtil.getRole(accessToken);

		// userEntity를 생성하여 값 set
		Member userEntity = Member.builder()
			.email(username)
			.password("tmppassword")
			.role(Role.valueOf(role))
			.build();

		// UserDetails에 회원 정보 객체 담기
		CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

		logger.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			customUserDetails,
			null,
			customUserDetails.getAuthorities());

		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
