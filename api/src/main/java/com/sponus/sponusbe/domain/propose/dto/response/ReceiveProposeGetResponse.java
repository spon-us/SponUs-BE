package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.LocalDate;

import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.ProposeStatus;

import lombok.Builder;

@Builder
public record ReceiveProposeGetResponse(
	Long id,
	Long organizationId,
	Long target,
	String organizationName,
	String organizationImageUrl,
	ProposeStatus status,
	LocalDate createdAt
) {

	public static ReceiveProposeGetResponse from(Propose propose) {
		return ReceiveProposeGetResponse.builder()
			.id(propose.getId())
			.organizationId(propose.getOrganization().getId())
			.target(propose.getTarget().getId())
			.organizationName(propose.getOrganization().getName())
			.organizationImageUrl(propose.getOrganization().getImageUrl())
			.status(propose.getStatus())
			.createdAt(propose.getCreatedAt().toLocalDate())
			.build();
	}
}
