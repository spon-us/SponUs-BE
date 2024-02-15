package com.sponus.sponusbe.domain.report.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.dto.request.ReportCreateRequest;
import com.sponus.sponusbe.domain.report.dto.response.ReportCreateResponse;
import com.sponus.sponusbe.domain.report.dto.response.ReportGetResponse;
import com.sponus.sponusbe.domain.report.dto.request.ReportUpdateRequest;
import com.sponus.sponusbe.domain.report.dto.response.ReportUpdateResponse;
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

	@PostMapping(consumes = "multipart/form-data")
	public ApiResponse<ReportCreateResponse> createReport(
		@AuthOrganization Organization authOrganization,
		@RequestPart("request") @Valid ReportCreateRequest request,
		@RequestPart(value = "images") List<MultipartFile> images,
		@RequestPart(value = "attachments") List<MultipartFile> attachments) throws IOException {
		return ApiResponse.onSuccess(
			reportService.createReport(
				authOrganization,
				request,
				images,
				attachments));
	}

	@PatchMapping(value = "/{reportId}", consumes = "multipart/form-data")
	public ApiResponse<ReportUpdateResponse> updateReport(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long reportId,
		@RequestPart("request") @Valid ReportUpdateRequest request,
		@RequestPart(value = "images") List<MultipartFile> images,
		@RequestPart(value = "attachments") List<MultipartFile> attachments) {
		return ApiResponse.onSuccess(
			reportService.updateReport(
				authOrganization,
				reportId,
				request,
				images,
				attachments));
	}

	@GetMapping("/{reportId}")
	public ApiResponse<ReportGetResponse> readReport(
		@PathVariable Long reportId
	) {
		return ApiResponse.onSuccess(reportQueryService.readReport(reportId));
	}

}
