package com.sponus.sponusbe.domain.organization.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.organization.Club;
import com.sponus.coredomain.domain.organization.Company;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.organization.company.dto.OrganizationGetResponse;
import com.sponus.sponusbe.domain.organization.controller.PageCondition;
import com.sponus.sponusbe.domain.organization.controller.PageResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationImageUploadResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSearchResponse;
import com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {
	private final OrganizationRepository organizationRepository;
	private final S3Service s3Service;
	private final PasswordEncoder passwordEncoder;

	public Long createOrganization(OrganizationCreateRequest request) {
		Organization organization;
		if (request.organizationType() == OrganizationType.COMPANY)
			organization = new Company(request.email(), passwordEncoder.encode(request.password()), request.name());
		else
			organization = new Club(request.email(), passwordEncoder.encode(request.password()), request.name());
		return organizationRepository.save(organization).getId();
	}

	public OrganizationImageUploadResponse uploadProfileImage(Long organizationId, MultipartFile file) {
		// TODO : 이미지 업로드 시, S3에 단체 ID를 태그 정보로 넣기
		Organization organization = findOrganizationById(organizationId);
		String imageUrl = s3Service.uploadImage(file);
		return new OrganizationImageUploadResponse(imageUrl);
	}

	public Boolean verifyName(String name) {
		return organizationRepository.existsByName(name);
	}

	public void deleteOrganization(Long organizationId) {
		Organization organization = findOrganizationById(organizationId);
		organization.delete();
	}

	private Organization findOrganizationById(Long organizationId) {
		return organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));
	}

	public PageResponse<OrganizationGetResponse> getOrganizations(
		PageCondition pageCondition,
		OrganizationType organizationType) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<OrganizationGetResponse> organizations = organizationRepository.findOrganizations(
				organizationType.name(), pageable).stream()
			.map(OrganizationGetResponse::of).toList();
		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByOrganizationType(organizationType.name())));
	}

	public PageResponse<OrganizationSearchResponse> searchOrganizations(PageCondition pageCondition, String keyword) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<OrganizationSearchResponse> organizations = organizationRepository.findByNameContains(
				keyword, pageable)
			.stream()
			.map(OrganizationSearchResponse::of)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByNameContains(keyword)));
	}
}
