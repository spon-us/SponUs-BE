package com.sponus.sponusbe.domain.organization.dto;

import java.util.List;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.tag.dto.TagGetResponse;

import lombok.Builder;

@Builder
public record OrganizationSummaryResponse(
	Long id,
	String name,
	String image,
	List<TagGetResponse> tags
) {
	public static OrganizationSummaryResponse from(Organization organization) {
		List<TagGetResponse> tags = TagGetResponse.getTagResponse(organization);

		return OrganizationSummaryResponse.builder()
			.id(organization.getId())
			.name(organization.getName())
			.image(organization.getImageUrl())
			.tags(tags)
			.build();
	}

}
