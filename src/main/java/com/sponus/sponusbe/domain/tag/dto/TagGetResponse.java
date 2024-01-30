package com.sponus.sponusbe.domain.tag.dto;

import java.util.List;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.tag.entity.Tag;

public record TagGetResponse(
	Long id,
	String name
) {
	public static TagGetResponse from(Tag tag) {
		return new TagGetResponse(tag.getId(), tag.getName());
	}

	public static List<TagGetResponse> getTagResponses(Organization organization) {
		return organization.getOrganizationTags()
			.stream()
			.map(organizationTag -> TagGetResponse.from(organizationTag.getTag()))
			.toList();
	}

}
