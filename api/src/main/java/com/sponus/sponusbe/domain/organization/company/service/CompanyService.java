package com.sponus.sponusbe.domain.organization.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.repository.CompanyRepository;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyCreateRequest;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyCreateResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;

	public ApiResponse<CompanyCreateResponse> createCompany(CompanyCreateRequest request) {
		return null;
	}

	public ApiResponse<Void> updateCompany(Long companyId, CompanyUpdateRequest request) {
		return null;
	}

	public ApiResponse<Void> deleteCompany(Long companyId) {
		return null;
	}
}
