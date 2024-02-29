package com.sponus.sponusbe.auth.jwt.dto;

public record JwtPair(
	String accessToken,
	String refreshToken
) {
}
