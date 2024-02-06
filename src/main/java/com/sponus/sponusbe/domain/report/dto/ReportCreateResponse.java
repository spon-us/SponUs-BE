package com.sponus.sponusbe.domain.report.dto;

import static com.sponus.sponusbe.domain.report.dto.ReportAttachmentResponse.*;

import java.util.List;

import com.sponus.sponusbe.domain.report.entity.Report;

import lombok.Builder;

@Builder
public record ReportCreateResponse(
	Long id,
	Long writerId,
	String title,
	String content
) {

	public static ReportCreateResponse from(Report report) {
		return ReportCreateResponse.builder()
			.id(report.getId())
			.writerId(report.getWriter().getId())
			.title(report.getTitle())
			.content(report.getContent())
			.build();
	}
}
