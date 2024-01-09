package com.sponus.sponusbe.group.dto;

import com.sponus.sponusbe.group.entity.Group;

import lombok.Builder;

@Builder
public record GroupJoinResponse(
	Long id,
	String email,
	String name
) {

	public static GroupJoinResponse from(Group entity) {
		return GroupJoinResponse.builder()
			.id(entity.getId())
			.email(entity.getEmail())
			.name(entity.getName())
			.build();
	}
}
