package com.sponus.sponusbe.auth.dto;

import com.sponus.coreinfrasecurity.jwt.dto.JwtPair;

import lombok.Builder;

@Builder
public record ReissueResponse(
	String accessToken,
	String refreshToken
) {

	public static ReissueResponse from(JwtPair jwtPair) {
		return ReissueResponse.builder()
			.accessToken(jwtPair.accessToken())
			.refreshToken(jwtPair.refreshToken())
			.build();
	}
}
