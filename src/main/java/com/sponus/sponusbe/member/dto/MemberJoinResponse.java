package com.sponus.sponusbe.member.dto;

import com.sponus.sponusbe.member.entity.Member;

import lombok.Builder;

@Builder
public record MemberJoinResponse(
	Long id,
	String email,
	String name
) {

	public static MemberJoinResponse from(Member entity) {
		return MemberJoinResponse.builder()
			.id(entity.getId())
			.email(entity.getEmail())
			.name(entity.getName())
			.build();
	}
}
