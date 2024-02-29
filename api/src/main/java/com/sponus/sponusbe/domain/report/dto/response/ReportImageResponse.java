package com.sponus.sponusbe.domain.report.dto.response;

import com.sponus.coredomain.domain.report.ReportImage;

import lombok.Builder;

@Builder
public record ReportImageResponse(
	Long id,
	String name,
	String url
) {

	public static ReportImageResponse from(ReportImage image) {
		return ReportImageResponse.builder()
			.id(image.getId())
			.name(image.getName())
			.url(image.getUrl())
			.build();
	}
}
