package com.sponus.sponusbe.domain.propose.service;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.coreinfrafirebase.FirebaseService;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.bookmark.exception.BookmarkErrorCode;
import com.sponus.sponusbe.domain.bookmark.exception.BookmarkException;
import com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProposeService {

	private final ProposeRepository proposeRepository;
	private final OrganizationRepository organizationRepository;

	// TODO 예외처리 수정
	public ProposeCreateResponse createPropose(Organization organization, ProposeCreateRequest request) {

		if (organization.getId().equals(request.target()))
			throw new ProposeException(ProposeErrorCode.PROPOSE_ERROR);

		final Organization target = organizationRepository.findById(request.target())
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));

		if (proposeRepository.existsByOrganization(target))
			throw new ProposeException(ProposeErrorCode.PROPOSE_ERROR);

		// long count = proposeRepository.findByOrganization(organization)
		// 	.stream()
		// 	.filter(propose -> propose.getCreatedAt().isAfter(LocalDateTime.now().toLocalDate().atStartOfDay()))
		// 	.count();

		Long count = proposeRepository.countProposesByOrganizationToday(organization,
			LocalDateTime.now().toLocalDate().atStartOfDay());

		if (count >= 5)
			throw new ProposeException(ProposeErrorCode.PROPOSE_LIMIT_ERROR);

		final Propose propose = proposeRepository.save(request.toEntity(organization, target));
		return ProposeCreateResponse.from(propose);
	}
}
