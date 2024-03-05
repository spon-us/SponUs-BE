package com.sponus.sponusbe.domain.tag.dto.request;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.tag.Tag;

public record TagCreateRequest(
	String name
) {
	public Tag toEntity(Organization organization) {
		return Tag.builder()
			.name(name)
			.organization(organization)
			.build();
	}
}
