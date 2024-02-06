package com.sponus.sponusbe.domain.report.dto;

import static com.sponus.sponusbe.domain.report.dto.ReportAttachmentResponse.*;

import java.util.List;

import com.sponus.sponusbe.domain.report.entity.Report;

import lombok.Builder;

@Builder
public record ReportCreateResponse(
	Long id,
	String title,
	String content,
	List<ReportAttachmentResponse> attachments
) {

	public static ReportCreateResponse from(Report report) {
		List<ReportAttachmentResponse> attachmentResponses = convertToAttachments(report.getReportAttachments());
		return ReportCreateResponse.builder()
			.id(report.getId())
			.title(report.getTitle())
			.content(report.getContent())
			.attachments(attachmentResponses)
			.build();
	}
}
