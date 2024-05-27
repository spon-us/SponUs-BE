package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.coredomain.domain.organization.enums.OrganizationType;

public record OrganizationCreateRequest(
	String email,
	String password,
	String name,
	OrganizationType organizationType
) {
}
