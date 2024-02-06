package com.sponus.sponusbe.domain.report.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.sponus.sponusbe.domain.report.entity.ReportAttachment;

import lombok.Builder;

@Builder
public record ReportAttachmentResponse(
	Long id,
	String name,
	String url
) {
}

