package com.sponus.sponusbe.domain.report.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.sponus.sponusbe.domain.report.entity.ReportAttachment;

import lombok.Builder;

@Builder
public record AttachmentResponse(
	Long id,
	String name,
	String url
) {

	public static List<AttachmentResponse> convertToAttachments(List<ReportAttachment> reportAttachments) {
		return reportAttachments.stream()
			.map(attachment -> AttachmentResponse.builder()
				.id(attachment.getId())
				.name(attachment.getName())
				.url(attachment.getUrl())
				.build())
			.collect(Collectors.toList());
	}
}

