package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

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
