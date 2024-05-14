package com.sponus.sponusbe.domain.propose.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.sponus.coredomain.domain.propose.Propose;

public record ProposeSummaryGetResponse(
	Long proposeId,
	String title,
	String status,
	Long proposedOrganizationId,
	String proposedOrganizationName,
	Long proposingOrganizationId,
	String proposingOrganizationName,
	String proposingOrganizationImageUrl,
	String createdDate,
	String createdDay
) {
	public static ProposeSummaryGetResponse from(Propose propose) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
		return new ProposeSummaryGetResponse(
			propose.getId(),
			propose.getTitle(),
			propose.getStatus().name(),
			propose.getProposedOrganization().getId(),
			propose.getProposedOrganization().getName(),
			propose.getProposingOrganization().getId(),
			propose.getProposingOrganization().getName(),
			propose.getProposingOrganization().getImageUrl(),
			propose.getCreatedAt().format(dateFormatter),
			propose.getUpdatedAt().format(dayFormatter)
		);
	}
}
