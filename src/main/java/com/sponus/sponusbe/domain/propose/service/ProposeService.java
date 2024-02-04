package com.sponus.sponusbe.domain.propose.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeUpdateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ProposeService {

	private final ProposeRepository proposeRepository;
	private final AnnouncementRepository announcementRepository;

	public ProposeCreateResponse createPropose(Organization authOrganization, ProposeCreateRequest request) {
		Announcement announcement = getAvailableAnnouncement(request.announcementId());
		return new ProposeCreateResponse(
			proposeRepository.save(
				request.toEntity(
					announcement,
					authOrganization,
					announcement.getWriter()
				)
			).getId()
		);
	}

	public void updatePropose(Organization authOrganization, Long proposeId, ProposeUpdateRequest request) {
		final Propose propose = getAccessablePropose(authOrganization, proposeId);
		propose.update(request.title(), request.content(), request.status());
	}

	public void deletePropose(Organization authOrganization, Long proposeId) {
		final Propose propose = getAccessablePropose(authOrganization, proposeId);
		proposeRepository.delete(propose);
	}

	private boolean isOrganizationsPropose(Long organizationId, Propose propose) {
		return propose.getProposingOrganization().getId().equals(organizationId);
	}

	private Announcement getAvailableAnnouncement(Long announcementId) {
		return announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
	}

	private Propose getAccessablePropose(Organization organization, Long proposeId) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));

		if (!isOrganizationsPropose(organization.getId(), propose))
			throw new ProposeException(ProposeErrorCode.INVALID_ORGANIZATION);

		return propose;
	}
}
