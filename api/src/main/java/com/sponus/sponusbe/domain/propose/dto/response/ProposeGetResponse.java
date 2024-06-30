package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.LocalDate;

import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.ProposeStatus;

import lombok.Builder;

@Builder
public record ProposeGetResponse(
	Long id,
	Long organizationId,
	Long target,
	String targetName,
	String targetImageUrl,
	ProposeStatus status,
	LocalDate createdAt
) {

	public static ProposeGetResponse from(Propose propose) {
		return ProposeGetResponse.builder()
			.id(propose.getId())
			.organizationId(propose.getOrganization().getId())
			.target(propose.getTarget().getId())
			.targetName(propose.getTarget().getName())
			.targetImageUrl(propose.getTarget().getImageUrl())
			.status(propose.getStatus())
			.createdAt(propose.getCreatedAt().toLocalDate())
			.build();
	}
}
