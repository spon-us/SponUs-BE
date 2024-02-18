package com.sponus.sponusbe.domain.organization.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;

import java.util.List;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.domain.notification.entity.Notification;
import com.sponus.sponusbe.domain.notification.exception.NotificationErrorCode;
import com.sponus.sponusbe.domain.notification.exception.NotificationException;
import com.sponus.sponusbe.domain.notification.repository.NotificationRepository;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSummaryResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationUpdateRequest;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;
import com.sponus.sponusbe.domain.s3.S3Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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
	private final JavaMailSender emailSender;
	private final S3Service s3Service;

	public OrganizationJoinResponse join(OrganizationJoinRequest request) {
		final Organization organization = organizationRepository.save(
			request.toEntity(passwordEncoder.encode(request.password())));
		return OrganizationJoinResponse.from(organization);
	}

	public void updateOrganization(Long organizationId, OrganizationUpdateRequest request, MultipartFile attachment) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));

		organization.update(request);
		if (attachment != null) {
			s3Service.deleteImage(organization.getImageUrl());
			String newUrl = s3Service.uploadFile(attachment);
			organization.updateImageUrl(newUrl);
		}
	}

	public void deactivateOrganization(Long organizationId) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));
		organization.deactivate();
	}

	public String sendEmail(String to) throws Exception {
		String code = createEmailCode();
		MimeMessage message = createEmail(to, code);
		emailSender.send(message);
		return code;
	}

	private MimeMessage createEmail(String to, String code) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("인증 코드 : " + code);
		MimeMessage message = emailSender.createMimeMessage();
		message.addRecipients(MimeMessage.RecipientType.TO, to); // 보내는 대상
		message.setSubject("Spon-us 인증 코드 발급입니다."); // 제목

		String msgg = "";
		msgg += "<div style='margin:20px;'>";
		msgg += "<h1> Spon-us 인증 코드입니다. </h1>"; // 동적으로 메시지 내용 변경
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> 인증 코드 </h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "인증 코드 : <strong>";
		msgg += code + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html"); // 내용
		message.setFrom(new InternetAddress("Spon-us_team", "Spon-us")); // 보내는 사람

		return message;
	}

	public static String createEmailCode() {
		StringBuilder code = new StringBuilder();
		Random rnd = new Random();

		for (int i = 0; i < 6; i++) { // 인증 코드 6자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤
			switch (index) {
				case 0:
					code.append((char)((rnd.nextInt(26)) + 97));
					//  a~z  (ex. 1+97=98 => (char)98 = 'b')
					break;
				case 1:
					code.append((char)((rnd.nextInt(26)) + 65));
					//  A~Z
					break;
				default:
					code.append((rnd.nextInt(10)));
					// 0~9
					break;
			}
		}
		return code.toString();
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
