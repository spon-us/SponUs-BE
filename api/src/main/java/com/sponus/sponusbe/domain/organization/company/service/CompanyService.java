package com.sponus.sponusbe.domain.organization.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.company.CollaborationType;
import com.sponus.coredomain.domain.organization.company.Company;
import com.sponus.coredomain.domain.organization.company.CompanyType;
import com.sponus.coredomain.domain.organization.repository.CollaborationRepository;
import com.sponus.coredomain.domain.organization.repository.CompanyRepository;
import com.sponus.coredomain.domain.organization.repository.CompanyTypeRepository;
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
	private final CompanyTypeRepository companyTypeRepository;
	private final CollaborationRepository collaborationRepository;

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
			request.profileStatus(),
			request.sponsorshipContent()
		);
		request.companyTypes().forEach(type -> {
			final CompanyType companyType = new CompanyType(type);
			companyType.updateCompany(company);
			companyTypeRepository.save(companyType);
		});
		request.collaborationTypes().forEach(type -> {
			final CollaborationType collaborationType = new CollaborationType(type);
			collaborationType.updateCompany(company);
			collaborationRepository.save(collaborationType);
		});
	}

	private Company findCompanyById(Long companyId) {
		return companyRepository.findById(companyId)
			.orElseThrow(() -> new OrganizationException(CompanyErrorCode.COMPANY_NOT_FOUND));
	}
}
