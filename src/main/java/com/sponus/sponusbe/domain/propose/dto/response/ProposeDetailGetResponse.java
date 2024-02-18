package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementDetailResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;

public record ProposeDetailGetResponse(
	Long proposeId,
	String title,
	String content,
	String status,
	Long proposedOrganizationId,
	String proposedOrganizationName,
	String proposedOrganizationImage,
	Long proposingOrganizationId,
	String proposingOrganizationName,
	String proposingOrganizationImage,
	List<ProposeAttachmentResponse> proposeAttachmentUrl,
	AnnouncementDetailResponse announcementDetails,
	String createdDate,
	String createdDay
) {
	public static ProposeDetailGetResponse from(Propose propose) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
		List<ProposeAttachmentResponse> attachmentUrls = propose.getProposeAttachments()
			.stream()
			.map(ProposeAttachmentResponse::from)
			.toList();
		AnnouncementDetailResponse announcementDetails = AnnouncementDetailResponse.from(propose.getAnnouncement(),
			false);
		return new ProposeDetailGetResponse(
			propose.getId(),
			propose.getTitle(),
			propose.getContent(),
			propose.getStatus().name(),
			propose.getProposedOrganization().getId(),
			propose.getProposedOrganization().getName(),
			propose.getProposedOrganization().getImageUrl(),
			propose.getProposingOrganization().getId(),
			propose.getProposingOrganization().getName(),
			propose.getProposingOrganization().getImageUrl(),
			attachmentUrls,
			announcementDetails,
			propose.getCreatedAt().format(dateFormatter),
			propose.getUpdatedAt().format(dayFormatter)
		);
	}
}
