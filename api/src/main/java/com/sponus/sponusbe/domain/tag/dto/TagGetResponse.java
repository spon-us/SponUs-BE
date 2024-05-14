package com.sponus.sponusbe.domain.tag.dto;

import java.util.List;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.tag.Tag;

public record TagGetResponse(
	Long id,
	String name
) {
	public static TagGetResponse from(Tag tag) {
		return new TagGetResponse(tag.getId(), tag.getName());
	}

	public static List<TagGetResponse> getTagResponse(Organization organization) {
		return organization.getTags()
			.stream()
			.map(TagGetResponse::from)
			.toList();
	}

}
