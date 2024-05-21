package com.sponus.sponusbe.domain.organization.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfras3.S3Service;
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
}
