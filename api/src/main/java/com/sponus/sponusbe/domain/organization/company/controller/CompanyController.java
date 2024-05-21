package com.sponus.sponusbe.domain.organization.company.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyGetResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyUpdateRequest;
import com.sponus.sponusbe.domain.organization.company.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;

	@GetMapping("/{companyId}")
	public ApiResponse<CompanyGetResponse> getCompany(@PathVariable Long companyId) {
		return ApiResponse.onSuccess(companyService.getCompany(companyId));
	}

	@PatchMapping("/{companyId}")
	public ApiResponse<Void> updateCompany(
		@PathVariable Long companyId,
		@Valid @RequestBody CompanyUpdateRequest request) {
		companyService.updateCompany(companyId, request);
		return ApiResponse.onSuccess(null);
	}
}
