package com.sponus.sponusbe.domain.organization.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.notification.repository.NotificationRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfraemail.EmailUtil;
import com.sponus.coreinfras3.S3Util;
import com.sponus.sponusbe.domain.notification.exception.NotificationErrorCode;
import com.sponus.sponusbe.domain.notification.exception.NotificationException;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSummaryResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationUpdateRequest;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OrganizationService {

	private final OrganizationRepository organizationRepository;
	private final NotificationRepository notificationRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3Util s3Util;
	private final EmailUtil emailUtil;

	public OrganizationJoinResponse join(OrganizationJoinRequest request) {
		final Organization organization = organizationRepository.save(
			request.toEntity(passwordEncoder.encode(request.password())));
		return OrganizationJoinResponse.from(organization);
	}

	public void updateOrganization(Long organizationId, OrganizationUpdateRequest request, MultipartFile attachment) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));

		organization.update(
			request.name(), request.email(), request.password(), request.location(), request.description(),
			request.organizationType(), request.suborganizationType(), request.managerName(), request.managerPosition(),
			request.managerEmail(), request.managerPhone(), request.managerAvailableDay(),
			request.managerAvailableHour(),
			request.managerContactPreference()
		);
		if (attachment != null) {
			s3Util.deleteFile(organization.getImageUrl());
			String newUrl = s3Util.uploadFile(attachment);
			organization.updateImageUrl(newUrl);
		}
	}

	public void deactivateOrganization(Long organizationId) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));
		organization.deactivate();
	}

	public String sendEmail(String to) throws Exception {
		return emailUtil.sendEmail(to);
	}

	public List<OrganizationSummaryResponse> searchOrganization(String keyword) {
		return organizationRepository.findByNameContains(keyword)
			.stream()
			.map(OrganizationSummaryResponse::from)
			.toList();
	}

	public void deleteNotification(Organization organization, Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));
		if (!notification.getOrganization().getId().equals(organization.getId())) {
			throw new NotificationException(NotificationErrorCode.INVALID_ORGANIZATION);
		}
		notificationRepository.delete(notification);
	}

	public void readNotification(Organization organization, Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));
		if (!notification.getOrganization().getId().equals(organization.getId())) {
			throw new NotificationException(NotificationErrorCode.INVALID_ORGANIZATION);
		}
		notification.setRead(true);
	}
}
