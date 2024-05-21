package com.sponus.sponusbe.domain.organization.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Company;
import com.sponus.coredomain.domain.organization.repository.CompanyRepository;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyGetResponse;
import com.sponus.sponusbe.domain.organization.company.dto.CompanyUpdateRequest;
import com.sponus.sponusbe.domain.organization.exception.CompanyErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;

	public CompanyGetResponse getCompany(Long companyId) {
		final Company company = findCompanyById(companyId);
		return CompanyGetResponse.of(company);
	}

	public void updateCompany(Long companyId, CompanyUpdateRequest request) {
		final Company company = findCompanyById(companyId);
		company.updateInfo(
			request.name(),
			request.description(),
			request.imageUrl(),
			request.collaborationType(),
			request.sponsorshipContent(),
			request.companyType(),
			request.profileStatus()
		);
	}

	public void deleteCompany(Long companyId) {
		final Company company = findCompanyById(companyId);
		company.delete();
	}

	private Company findCompanyById(Long companyId) {
		return companyRepository.findById(companyId)
			.orElseThrow(() -> new OrganizationException(CompanyErrorCode.COMPANY_NOT_FOUND));
	}
}
