package com.sponus.sponusbe.domain.report.dto;

import static com.sponus.sponusbe.domain.report.dto.AttachmentResponse.*;

import java.util.List;

import com.sponus.sponusbe.domain.report.entity.Report;

import lombok.Builder;

@Builder
public record ReportResponse(
	Long id,
	String title,
	String content,
	List<AttachmentResponse> attachments
) {

	public static ReportResponse from(Report report) {
		List<AttachmentResponse> attachmentResponses = convertToAttachments(report.getReportAttachments());
		return ReportResponse.builder()
			.id(report.getId())
			.title(report.getTitle())
			.content(report.getContent())
			.attachments(attachmentResponses)
			.build();
	}
}
