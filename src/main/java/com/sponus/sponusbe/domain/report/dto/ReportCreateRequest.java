package com.sponus.sponusbe.domain.report.dto;

import java.util.ArrayList;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.entity.Report;

import jakarta.validation.constraints.NotNull;

public record ReportCreateRequest(
	@NotNull(message = "[ERROR] 보고서 제목 입력은 필수 입니다.")
	String title,
	@NotNull(message = "[ERROR] 보고서 내용 입력은 필수 입니다.")
	String content
) {

	public Report toEntity(Organization writer) {
		return Report.builder()
			.writer(writer)
			.title(title)
			.content(content)
			.reportAttachments(new ArrayList<>())
			.build();
	}
}