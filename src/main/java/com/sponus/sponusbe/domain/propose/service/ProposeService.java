package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeUpdateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeDetailGetResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.entity.ProposeAttachment;
import com.sponus.sponusbe.domain.propose.entity.ProposeStatus;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;
import com.sponus.sponusbe.domain.s3.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProposeService {

	private final S3Service s3Service;
	private final ProposeRepository proposeRepository;
	private final AnnouncementRepository announcementRepository;

	public ProposeCreateResponse createPropose(
		Organization authOrganization,
		ProposeCreateRequest request,
		List<MultipartFile> attachments
	) {
		// 활성화된 공고만 제안 추가 가능
		Announcement announcement = getAvailableAnnouncement(request.announcementId());

		// 제안 생성
		final Propose propose = request.toEntity(
			announcement,
			announcement.getWriter(),
			authOrganization
		);

		// 제안의 첨부파일 업로드
		attachments.forEach(file -> {
			final String url = s3Service.uploadFile(file);
			ProposeAttachment proposeAttachment = ProposeAttachment.builder()
				.name(file.getOriginalFilename())
				.url(url)
				.build();
			proposeAttachment.setPropose(propose);
		});

		return new ProposeCreateResponse(
			proposeRepository.save(propose).getId()
		);
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

	public void updateProposeStatus(Organization authOrganization, Long proposeId, ProposeStatus status) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
		// 제안을 "받은" 단체만 가능
		if (!isProposedOrganization(authOrganization.getId(), propose))
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSED_ORGANIZATION);
		propose.updateStatus(status);
	}

	private Announcement getAvailableAnnouncement(Long announcementId) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		if (!announcement.isAvailable())
			throw new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_IN_PROGRESS);
		return announcement;
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
