package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementSummaryResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.report.entity.Report;

public record ProposeSummaryGetResponse(
	Long proposeId,
	String title,
	String status,
	Long proposedOrganizationId,
	String proposedOrganizationName,
	Long proposingOrganizationId,
	String proposingOrganizationName,
	boolean isReported,
	Long reportId,
	AnnouncementSummaryResponse announcementSummary,
	String createdDate,
	String createdDay
) {
	public static ProposeSummaryGetResponse from(Propose propose) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
		AnnouncementSummaryResponse announcementSummary = AnnouncementSummaryResponse.from(propose.getAnnouncement());
		Report report = propose.getReport();
		return new ProposeSummaryGetResponse(
			propose.getId(),
			propose.getTitle(),
			propose.getStatus().name(),
			propose.getProposedOrganization().getId(),
			propose.getProposedOrganization().getName(),
			propose.getProposingOrganization().getId(),
			propose.getProposingOrganization().getName(),
			report != null,
			report != null ? report.getId() : null,
			announcementSummary,
			propose.getCreatedAt().format(dateFormatter),
			propose.getUpdatedAt().format(dayFormatter)
		);
	}
}
