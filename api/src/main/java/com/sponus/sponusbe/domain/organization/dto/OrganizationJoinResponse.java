package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.coredomain.domain.organization.Organization;

import lombok.Builder;

@Builder
public record OrganizationJoinResponse(
	Long id,
	String email,
	String name
) {

	public static OrganizationJoinResponse from(Organization organization) {
		return OrganizationJoinResponse.builder()
			.id(organization.getId())
			.email(organization.getEmail())
			.name(organization.getName())
			.build();
	}
}
