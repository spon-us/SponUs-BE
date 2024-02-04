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

	public static List<TagGetResponse> getTagResponse(Organization organization) {
		return organization.getTags()
			.stream()
			.map(TagGetResponse::from)
			.toList();
	}

}
