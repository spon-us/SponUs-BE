package com.sponus.sponusbe.domain.propose.dto.response;

import com.sponus.coredomain.domain.propose.Propose;

import lombok.Builder;

@Builder
public record ProposeCreateResponse(
	Long id,
	Long organizationId,
	Long target
) {

	public static ProposeCreateResponse from(Propose propose) {
		return ProposeCreateResponse.builder()
			.id(propose.getId())
			.organizationId(propose.getOrganization().getId())
			.target(propose.getTarget().getId())
			.build();
	}
}
