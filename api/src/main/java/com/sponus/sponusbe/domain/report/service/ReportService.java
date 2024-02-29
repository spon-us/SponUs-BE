package com.sponus.sponusbe.domain.report.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.coredomain.domain.report.Report;
import com.sponus.coredomain.domain.report.ReportAttachment;
import com.sponus.coredomain.domain.report.ReportImage;
import com.sponus.coredomain.domain.report.repository.ReportRepository;
import com.sponus.sponusbe.domain.notification.service.FirebaseService;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.report.dto.request.ReportCreateRequest;
import com.sponus.sponusbe.domain.report.dto.request.ReportUpdateRequest;
import com.sponus.sponusbe.domain.report.dto.response.ReportCreateResponse;
import com.sponus.sponusbe.domain.report.dto.response.ReportUpdateResponse;
import com.sponus.sponusbe.domain.report.exception.ReportErrorCode;
import com.sponus.sponusbe.domain.report.exception.ReportException;
import com.sponus.sponusbe.domain.s3.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

	private final ReportRepository reportRepository;
	private final ProposeRepository proposeRepository;
	private final S3Service s3Service;
	private final FirebaseService firebaseService;

	public ReportCreateResponse createReport(
		Organization authOrganization,
		ReportCreateRequest request,
		List<MultipartFile> images,
		List<MultipartFile> attachments
	) throws IOException {
		final Report report = request.toEntity(authOrganization);
		setReportImages(images, report);
		setReportAttachments(attachments, report);

		final Propose propose = proposeRepository.findById(request.proposeId())
			.orElseThrow(() -> new ReportException(ProposeErrorCode.PROPOSE_NOT_FOUND));

		report.setPropose(propose);

		firebaseService.sendMessageTo(propose.getAnnouncement().getWriter(), "보고서 도착",
			authOrganization.getName() + " 담당자님이 보고서를 보냈습니다.", report.getPropose().getAnnouncement(), propose, report);

		return ReportCreateResponse.from(reportRepository.save(report));
	}

	public ReportUpdateResponse updateReport(
		Organization authOrganization,
		Long reportId,
		ReportUpdateRequest request,
		List<MultipartFile> images,
		List<MultipartFile> attachments) {
		final Report report = reportRepository.findById(reportId)
			.orElseThrow(() -> new ReportException(ReportErrorCode.REPORT_NOT_FOUND));

		if (!isOrganizationsReport(authOrganization.getId(), report))
			throw new ReportException(ReportErrorCode.INVALID_ORGANIZATION);

		report.update(request.title(), request.content());
		setReportImages(images, report);
		setReportAttachments(attachments, report);

		reportRepository.save(report);
		return ReportUpdateResponse.from(report);
	}

	private boolean isOrganizationsReport(Long organizationId, Report report) {
		return report.getWriter().getId().equals(organizationId);
	}

	private void setReportImages(List<MultipartFile> images, Report report) {
		report.getReportImages().clear();
		images.forEach(image -> {
			final String url = s3Service.uploadFile(image);
			ReportImage reportImage = ReportImage.builder()
				.name(image.getOriginalFilename())
				.url(url)
				.build();
			reportImage.setReport(report);
		});
	}

	private void setReportAttachments(List<MultipartFile> attachments, Report report) {
		report.getReportAttachments().clear();
		attachments.forEach(attachment -> {
			final String url = s3Service.uploadFile(attachment);
			ReportAttachment reportAttachment = ReportAttachment.builder()
				.name(attachment.getOriginalFilename())
				.url(url)
				.build();
			reportAttachment.setReport(report);
		});
	}

}

