package com.sponus.sponusbe.domain.report.dto;

import java.util.List;

import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementImageResponse;
import com.sponus.sponusbe.domain.report.entity.Report;

import lombok.Builder;

@Builder
public record ReportGetResponse(
	Long id,
	Long writerId,
	String title,
	String content,
	List<ReportImageResponse> reportImages
) {

	public static ReportGetResponse from(Report report) {
		List<ReportImageResponse> reportImages = report.getReportImages()
			.stream()
			.map(ReportImageResponse::from)
			.toList();

		return ReportGetResponse.builder()
			.id(report.getId())
			.writerId(report.getWriter().getId())
			.title(report.getTitle())
			.content(report.getContent())
			.reportImages(reportImages)
			.build();
	}
}
