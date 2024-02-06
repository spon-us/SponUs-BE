package com.sponus.sponusbe.domain.report.dto;

import com.sponus.sponusbe.domain.report.entity.ReportAttachment;
import com.sponus.sponusbe.domain.report.entity.ReportImage;

import lombok.Builder;

@Builder
public record ReportAttachmentResponse(
	Long id,
	String name,
	String url
) {

	public static ReportAttachmentResponse from(ReportAttachment attachment) {
		return ReportAttachmentResponse.builder()
			.id(attachment.getId())
			.name(attachment.getName())
			.url(attachment.getUrl())
			.build();
	}
}

