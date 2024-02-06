package com.sponus.sponusbe.domain.report.dto;

import com.sponus.sponusbe.domain.report.entity.ReportImage;

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
