package com.sponus.sponusbe.domain.report.dto;

import lombok.Builder;

@Builder
public record ReportAttachmentResponse(
	Long id,
	String name,
	String url
) {
}

