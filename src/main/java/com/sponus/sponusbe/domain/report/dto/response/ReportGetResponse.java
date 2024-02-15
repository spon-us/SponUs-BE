package com.sponus.sponusbe.domain.report.dto.response;

import java.util.List;

import com.sponus.sponusbe.domain.report.entity.Report;

import lombok.Builder;

@Builder
public record ReportGetResponse(
	Long id,
	Long writerId,
	String title,
	String content,
	List<ReportImageResponse> reportImages,
	List<ReportAttachmentResponse> reportAttachments
) {

	public static ReportGetResponse from(Report report) {
		List<ReportImageResponse> reportImages = report.getReportImages()
			.stream()
			.map(ReportImageResponse::from)
			.toList();
		List<ReportAttachmentResponse> reportAttachments = report.getReportAttachments()
			.stream()
			.map(ReportAttachmentResponse::from)
			.toList();

		return ReportGetResponse.builder()
			.id(report.getId())
			.writerId(report.getWriter().getId())
			.title(report.getTitle())
			.content(report.getContent())
			.reportImages(reportImages)
			.reportAttachments(reportAttachments)
			.build();
	}
}
