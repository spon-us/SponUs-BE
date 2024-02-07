package com.sponus.sponusbe.domain.organization.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;

import java.util.List;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSummaryResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationUpdateRequest;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class OrganizationService {

	private final OrganizationRepository organizationRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender emailSender;

	public OrganizationJoinResponse join(OrganizationJoinRequest request) {
		final Organization organization = organizationRepository.save(
			request.toEntity(passwordEncoder.encode(request.password())));
		return OrganizationJoinResponse.from(organization);
	}

	public void updateOrganization(Long organizationId, OrganizationUpdateRequest request) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));
		organization.update(request);
	}

	public void deactivateOrganization(Long organizationId) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));
		organization.deactivate();
	}

	public String sendEmail(String to) throws Exception {
		String code = createEmailCode();
		MimeMessage message = createMessage(to, code);
		emailSender.send(message);
		return code;
	}

	private MimeMessage createMessage(String to, String code) throws Exception {
		System.out.println("보내는 대상 : " + to);
		System.out.println("인증 코드 : " + code);
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
		StringBuffer code = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 비밀번호 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
				case 0:
					code.append((char)((int)(rnd.nextInt(26)) + 97));
					//  a~z  (ex. 1+97=98 => (char)98 = 'b')
					break;
				case 1:
					code.append((char)((int)(rnd.nextInt(26)) + 65));
					//  A~Z
					break;
				case 2:
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
}
