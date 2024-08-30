package com.sponus.sponusbe.domain.propose.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.notification.NotificationStatus;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.coreinfrafirebase.FirebaseUtil;
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
	private final FirebaseUtil firebaseUtil;

	public ProposeCreateResponse createPropose(Organization organization, ProposeCreateRequest request) {

		if (organization.getId().equals(request.target()))
			throw new ProposeException(ProposeErrorCode.CANNOT_PROPOSE_TO_SELF);

		final Organization target = organizationRepository.findById(request.target())
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));

		if (organization.getImageUrl() == null || organization.getImageUrl().isEmpty())
			throw new ProposeException(ProposeErrorCode.PROFILE_NOT_COMPLETED);

		final Long count = proposeRepository.countProposesByOrganizationToday(organization,
			LocalDateTime.now().toLocalDate().atStartOfDay());

		if (count >= 5)
			throw new ProposeException(ProposeErrorCode.PROPOSE_LIMIT_ERROR);

		// TODO 쿼리와 비교
		// long count = proposeRepository.findByOrganization(organization)
		// 	.stream()
		// 	.filter(propose -> propose.getCreatedAt().isAfter(LocalDateTime.now().toLocalDate().atStartOfDay()))
		// 	.count();

		final Propose propose = proposeRepository.save(request.toEntity(organization, target));
		try {
			firebaseUtil.sendMessageTo(
				target,
				organization.getName() + "(으)로부터 제안이 왔어요!",
				"이메일을 확인하고 기업과 컨택해 보세요.",
				propose,
				NotificationStatus.RECEIVE
			);
		} catch (IOException ex) {
			log.error("[*] Failed to send notification to organization: " + target.getName(), ex);
		}
		return ProposeCreateResponse.from(propose);
	}
}
