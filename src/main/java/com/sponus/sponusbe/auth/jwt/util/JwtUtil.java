package com.sponus.sponusbe.auth.jwt.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode;
import com.sponus.sponusbe.auth.user.CustomUserDetails;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private static final String AUTHORITIES_CLAIM_NAME = "auth";

	private final SecretKey secretKey;
	private final Long accessExpMs;
	private final Long refreshExpMs;
	private final RedisUtil redisUtil;

	public JwtUtil(
		@Value("${spring.jwt.secret}") String secret,
		@Value("${spring.jwt.token.access-expiration-time}") Long access,
		@Value("${spring.jwt.token.refresh-expiration-time}") Long refresh,
		RedisUtil redis) {

		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		accessExpMs = access;
		refreshExpMs = refresh;
		redisUtil = redis;
	}

	public Long getId(String token) {
		return Long.parseLong(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.getSubject());
	}

	public String getEmail(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("email", String.class);
	}

	public String getAuthority(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get(AUTHORITIES_CLAIM_NAME, String.class);
	}

	public Boolean isExpired(String token) {
		// 여기서 토큰 형식 이상한 것도 걸러짐
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.before(Date.from(Instant.now()));
	}

	public Long getExpTime(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.getTime();
	}

	public String createJwtAccessToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(accessExpMs);

		return Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.subject(customUserDetails.getId().toString())
			.claim("email", customUserDetails.getUsername())
			.claim(AUTHORITIES_CLAIM_NAME, customUserDetails.getAuthority())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtRefreshToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(refreshExpMs);

		String refreshToken = Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.subject(customUserDetails.getId().toString())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();

		redisUtil.save(
			refreshToken,
			refreshToken,
			refreshExpMs,
			TimeUnit.MILLISECONDS
		);

		return refreshToken;
	}

	public JwtPair reissueToken(String refreshToken) {
		// TODO: 임시메서드(작동안함). Repository에 대한 의존성을 가져야해서 Service로 빼야함
		CustomUserDetails tempCustomUserDetails = new CustomUserDetails(
			getId(refreshToken),
			getEmail(refreshToken),
			null,
			getAuthority(refreshToken)
		);

		return new JwtPair(
			createJwtAccessToken(tempCustomUserDetails),
			createJwtRefreshToken(tempCustomUserDetails)
		);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer ")) {

			log.warn("[*] No token in req");

			return null;
		}

		log.info("[*] Token exists");

		return authorization.split(" ")[1];
	}

	public boolean validateRefreshToken(String refreshToken) {
		// refreshToken 유효성 검증
		String email = getEmail(refreshToken);

		//redis에 refreshToken 있는지 검증
		if (!redisUtil.hasKey(email)) {
			log.warn("[*] case : Invalid refreshToken");
			throw new SecurityCustomException(SecurityErrorCode.INVALID_TOKEN);
		}
		return true;
	}
}
