package com.sponus.sponusbe.domain.propose.dto.request;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.ProposeStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProposeCreateRequest(
	@NotNull
	Long target
) {
	public Propose toEntity(Organization organization, Organization target) {
		return Propose.builder()
			.organization(organization)
			.target(target)
			.build();
	}
}
