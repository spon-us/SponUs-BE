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
	public ApiResponse<ReportResponse> create(@Valid @RequestBody ReportRequest request) {
		ReportResponse response = reportService.create(request);
		return ApiResponse.onSuccess(response);
	}

	@PatchMapping("/{reportId}")
	public ApiResponse<ReportResponse> update(@PathVariable Long reportId, @Valid @RequestBody ReportRequest request) {
		ReportResponse response = reportService.update(reportId, request);
		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/{reportId}")
	public ApiResponse<ReportResponse> read(@PathVariable Long reportId) {
		ReportResponse response = reportQueryService.read(reportId);
		return ApiResponse.onSuccess(response);
	}

}
