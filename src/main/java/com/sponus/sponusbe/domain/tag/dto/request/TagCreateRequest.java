package com.sponus.sponusbe.domain.tag.dto.request;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.tag.entity.Tag;

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
