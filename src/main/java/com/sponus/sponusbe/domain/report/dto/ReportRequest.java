package com.sponus.sponusbe.domain.report.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.domain.report.entity.Report;

import jakarta.validation.constraints.NotNull;

public record ReportRequest(
	@NotNull(message = "[ERROR] 보고서 제목 입력은 필수 입니다.")
	String title,

	@NotNull(message = "[ERROR] 보고서 내용 입력은 필수 입니다.")
	String content,

	@NotNull(message = "[ERROR] 첨부 파일은 필수입니다.")
	List<MultipartFile> files
) {

	public Report toEntity() {
		return Report.builder()
			.title(title)
			.content(content)
			.reportAttachments(new ArrayList<>())
			.build();
	}
}
