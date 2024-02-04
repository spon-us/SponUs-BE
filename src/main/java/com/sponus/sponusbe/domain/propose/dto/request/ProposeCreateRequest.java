package com.sponus.sponusbe.domain.propose.dto.request;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.entity.ProposeStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProposeCreateRequest(

	@NotBlank(message = "[ERROR] 제안 제목 입력은 필수 입니다.")
	String title,

	@NotBlank(message = "[ERROR] 제안 내용 입력은 필수 입니다.")
	String content,

	@NotNull(message = "[ERROR] 제안 대상 공고 입력은 필수 입니다.")
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
