package com.sponus.sponusbe.domain.propose.dto.request;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.entity.ProposeStatus;

public record ProposeCreateRequest(
	String title,
	String content,
	Long announcementId
) {
	public Propose toEntity(
		Announcement announcement,
		Organization proposedOrganization,
		Organization proposingOrganization
	) {
		return Propose.builder()
			.title(title)
			.content(content)
			.status(ProposeStatus.PENDING)
			.announcement(announcement)
			.proposedOrganization(proposedOrganization)
			.proposingOrganization(proposingOrganization)
			.build();
	}
}
