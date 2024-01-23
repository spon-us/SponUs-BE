package com.sponus.sponusbe.domain.propose.controller.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.sponus.sponusbe.domain.propose.entity.Propose;

public record ProposeDetailGetResponse(
	Long proposeId,
	String title,
	String content,
	String status,
	String proposeImageUrl,
	Long proposedOrganizationId,
	String proposedOrganizationName,
	Long proposingOrganizationId,
	String proposingOrganizationName,

	// TODO : 공고 상세 정보 (공고에서 묶기!)
	Long announcementId,
	String createdDate,
	String createdDay
) {
	public static ProposeDetailGetResponse from(Propose propose) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
		return new ProposeDetailGetResponse(
			propose.getId(),
			propose.getTitle(),
			propose.getContent(),
			propose.getStatus().name(),
			null, // TODO : 제안 이미지 URL
			propose.getProposedOrganization().getId(),
			propose.getProposedOrganization().getName(),
			propose.getProposingOrganization().getId(),
			propose.getProposingOrganization().getName(),
			propose.getAnnouncement().getId(),
			propose.getCreatedAt().format(dateFormatter),
			propose.getUpdatedAt().format(dayFormatter)
		);
	}
}
