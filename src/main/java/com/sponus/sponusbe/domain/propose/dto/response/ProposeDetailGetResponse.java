package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.entity.ProposeAttachment;

public record ProposeDetailGetResponse(
	Long proposeId,
	String title,
	String content,
	String status,
	Long proposedOrganizationId,
	String proposedOrganizationName,
	Long proposingOrganizationId,
	String proposingOrganizationName,
	List<String> proposeAttachmentUrl,

	// TODO : 공고 상세 정보 (공고에서 묶기!)
	Long announcementId,
	String createdDate,
	String createdDay
) {
	public static ProposeDetailGetResponse from(Propose propose) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
		List<String> attachmentUrls = propose.getProposeAttachments().stream().map(ProposeAttachment::getUrl).toList();

		return new ProposeDetailGetResponse(
			propose.getId(),
			propose.getTitle(),
			propose.getContent(),
			propose.getStatus().name(),
			propose.getProposedOrganization().getId(),
			propose.getProposedOrganization().getName(),
			propose.getProposingOrganization().getId(),
			propose.getProposingOrganization().getName(),
			attachmentUrls,
			propose.getAnnouncement().getId(),
			propose.getCreatedAt().format(dateFormatter),
			propose.getUpdatedAt().format(dayFormatter)
		);
	}
}
