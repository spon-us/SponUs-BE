package com.sponus.sponusbe.auth.dto;

import lombok.Builder;

@Builder
public record EmailVerificationResponse(
	String email,
	String exist
) {

	public static EmailVerificationResponse from(String email, String exist){
		return EmailVerificationResponse.builder()
			.email(email)
			.exist(exist)
			.build();
	}
}
