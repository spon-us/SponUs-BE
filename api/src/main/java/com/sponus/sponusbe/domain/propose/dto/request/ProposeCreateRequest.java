package com.sponus.sponusbe.domain.propose.dto.request;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.ProposeStatus;

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
		Organization proposedOrganization,
		Organization proposingOrganization
	) {
		return Propose.builder()
			.title(title)
			.content(content)
			.status(ProposeStatus.PENDING)
			.proposedOrganization(proposedOrganization)
			.proposingOrganization(proposingOrganization)
			.build();
	}
}
