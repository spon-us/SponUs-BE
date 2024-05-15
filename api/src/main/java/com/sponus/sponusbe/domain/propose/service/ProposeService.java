package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.ProposeAttachment;
import com.sponus.coredomain.domain.propose.ProposeStatus;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.coreinfrafirebase.FirebaseService;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeStatusUpdateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeUpdateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeDetailGetResponse;
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
	private final S3Service s3Service;
	private final FirebaseService firebaseService;

	public ProposeCreateResponse createPropose(
		Organization authOrganization,
		ProposeCreateRequest request,
		List<MultipartFile> attachments) {
		return null;
	}

	public ProposeDetailGetResponse getProposeDetail(
		Organization authOrganization,
		Long proposeId) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
		// 제안을 받은 단체가 조회할 경우 상태를 "VIEWED"으로 변경
		if (isProposedOrganization(authOrganization.getId(), propose))
			propose.updateToViewed();
		else if (!isProposingOrganization(authOrganization.getId(), propose))
			// 제안한 단체도 아닐 경우 조회 불가
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSING_ORGANIZATION);

		return ProposeDetailGetResponse.from(propose);
	}

	public void updatePropose(
		Organization authOrganization,
		Long proposeId,
		ProposeUpdateRequest request,
		List<MultipartFile> attachments) {
		final Propose propose = getUpdatablePropose(authOrganization, proposeId);
		propose.updateInfo(request.title(), request.content());
		updateProposeAttachments(propose, attachments);
	}

	public void deletePropose(Organization authOrganization, Long proposeId) {
		final Propose propose = getUpdatablePropose(authOrganization, proposeId);
		proposeRepository.delete(propose);
	}

	public void updateProposeStatus(Organization authOrganization, Long proposeId, ProposeStatusUpdateRequest status) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
		// 제안을 "받은" 단체만 가능
		if (!isProposedOrganization(authOrganization.getId(), propose))
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSED_ORGANIZATION);

		try {
			propose.updateStatus(ProposeStatus.of(status.status()));
		} catch (Exception e) {
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSE_STATUS);
		}
	}

	private Propose getUpdatablePropose(Organization organization, Long proposeId) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));

		if (!isProposingOrganization(organization.getId(), propose))
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSING_ORGANIZATION);

		if (propose.getStatus() != ProposeStatus.PENDING)
			throw new ProposeException(ProposeErrorCode.PROPOSE_STATUS_NOT_PENDING);

		return propose;
	}

	private boolean isProposingOrganization(Long organizationId, Propose propose) {
		return propose.getProposingOrganization().getId().equals(organizationId);
	}

	private boolean isProposedOrganization(Long organizationId, Propose propose) {
		return propose.getProposedOrganization().getId().equals(organizationId);
	}

	private void updateProposeAttachments(Propose propose, List<MultipartFile> attachments) {
		propose.getProposeAttachments().clear();
		attachments.forEach(attachment -> {
			final String url = s3Service.uploadFile(attachment);
			ProposeAttachment proposeAttachment = ProposeAttachment.builder()
				.name(attachment.getOriginalFilename())
				.url(url)
				.build();
			proposeAttachment.setPropose(propose);
		});
	}
}
