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
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.entity.ProposeAttachment;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;
import com.sponus.sponusbe.domain.s3.S3Service;

import lombok.RequiredArgsConstructor;

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
			authOrganization,
			announcement.getWriter()
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

	public void updatePropose(Organization authOrganization, Long proposeId, ProposeUpdateRequest request) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));

		if (!isOrganizationsPropose(authOrganization.getId(), propose))
			throw new ProposeException(ProposeErrorCode.INVALID_ORGANIZATION);

		propose.update(request.title(), request.content(), request.status());
	}

	private boolean isOrganizationsPropose(Long organizationId, Propose propose) {
		return propose.getProposingOrganization().getId().equals(organizationId);
	}

	private Announcement getAvailableAnnouncement(Long announcementId) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		if (!announcement.isAvailable())
			throw new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_IN_PROGRESS);
		return announcement;
	}

}
