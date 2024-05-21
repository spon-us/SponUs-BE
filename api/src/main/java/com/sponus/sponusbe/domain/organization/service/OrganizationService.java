package com.sponus.sponusbe.domain.organization.service;

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
import com.sponus.sponusbe.domain.organization.dto.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationImageUploadResponse;
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
		// TODO : 나중에 디자인 패턴 적용
		Organization organization = null;
		if (request.organizationType() == OrganizationType.COMPANY)
			organization = new Company(request.email(), passwordEncoder.encode(request.password()), request.name());
		else
			organization = new Club(request.email(), passwordEncoder.encode(request.password()), request.name());
		return organizationRepository.save(organization).getId();
	}

	public OrganizationImageUploadResponse uploadProfileImage(Long organizationId, MultipartFile file) {
		// TODO : 이미지 업로드 시, 단체 ID를 태그 정보로 넣기
		Organization organization = findOrganizationById(organizationId);
		String imageUrl = s3Service.uploadImage(file);
		return new OrganizationImageUploadResponse(imageUrl);
	}

	private Organization findOrganizationById(Long organizationId) {
		return organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));
	}

	public Boolean verifyName(String name) {
		return organizationRepository.existsByName(name);
	}

}
