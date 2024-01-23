package com.sponus.sponusbe.domain.propose.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.controller.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.controller.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ProposeService {

	private final ProposeRepository proposeRepository;

	public ProposeCreateResponse createPropose(Organization organization, ProposeCreateRequest request) {
		// TODO : 공지쪽 연관관계로 인해 동작 안함
		Announcement announcement = getAvailableAnnouncement(request.announcementId());
		Organization studentOrganization;
		Organization companyOrganization;
		if (organization.isStudentOrganization()) {
			studentOrganization = organization;
			companyOrganization = announcement.getWriter();
		} else {
			studentOrganization = announcement.getWriter();
			companyOrganization = organization;
		}
		return new ProposeCreateResponse(
			proposeRepository.save(
				request.toEntity(
					announcement,
					organization,
					studentOrganization,
					companyOrganization
				)
			).getId()
		);
	}

	private Announcement getAvailableAnnouncement(Long announcementId) {
		// TODO : announcementId로 announcement를 찾아서 반환
		// return announcementRepository.findById(announcementId)
		// 	.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		return Announcement.builder()
			.title("title")
			.type(AnnouncementType.COLLABORATION)
			.category(AnnouncementCategory.IDEA)
			.content("content")
			.status(AnnouncementStatus.POSTED)
			.build();
	}
}
