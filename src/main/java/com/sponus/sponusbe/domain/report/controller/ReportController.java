package com.sponus.sponusbe.domain.report.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.domain.report.dto.ReportRequest;
import com.sponus.sponusbe.domain.report.dto.ReportResponse;
import com.sponus.sponusbe.domain.report.service.ReportQueryService;
import com.sponus.sponusbe.domain.report.service.ReportService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

	private final ReportService reportService;
	private final ReportQueryService reportQueryService;

	@PostMapping
	public ApiResponse<ReportResponse> createReport(@Valid @RequestBody ReportRequest request) {
		return ApiResponse.onSuccess(reportService.createReport(request));
	}

	@PatchMapping("/{reportId}")
	public ApiResponse<ReportResponse> updateReport(@PathVariable Long reportId,
		@Valid @RequestBody ReportRequest request) {
		return ApiResponse.onSuccess(reportService.updateReport(reportId, request));
	}

	@GetMapping("/{reportId}")
	public ApiResponse<ReportResponse> readReport(@PathVariable Long reportId) {
		return ApiResponse.onSuccess(reportQueryService.readReport(reportId));
	}

}
