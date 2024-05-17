package com.sponus.sponusbe.domain.organization.company.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyCreateRequest;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyCreateResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyUpdateRequest;
import com.sponus.sponusbe.domain.organization.company.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;

	// TODO: CompanyId 토큰에서 가져올 것
	@PostMapping("/join")
	public ApiResponse<CompanyCreateResponse> createCompany(@Valid @RequestBody CompanyCreateRequest request) {
		return companyService.createCompany(request);
	}

	@PatchMapping("/{companyId}")
	public ApiResponse<Void> updateCompany(
		@PathVariable Long companyId,
		@Valid @RequestBody CompanyUpdateRequest request) {
		return companyService.updateCompany(companyId, request);
	}

	@DeleteMapping("/{companyId}")
	public ApiResponse<Void> deleteCompany(
		@PathVariable Long companyId) {
		return companyService.deleteCompany(companyId);
	}
}
