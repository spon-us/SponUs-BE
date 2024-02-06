package com.sponus.sponusbe.domain.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.dto.ReportCreateRequest;
import com.sponus.sponusbe.domain.report.dto.ReportCreateResponse;
import com.sponus.sponusbe.domain.report.entity.Report;
import com.sponus.sponusbe.domain.report.entity.ReportAttachment;
import com.sponus.sponusbe.domain.report.entity.ReportImage;
import com.sponus.sponusbe.domain.report.exception.ReportErrorCode;
import com.sponus.sponusbe.domain.report.exception.ReportException;
import com.sponus.sponusbe.domain.report.repository.ReportRepository;
import com.sponus.sponusbe.domain.s3.S3Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ReportService {
	private final ReportRepository reportRepository;
	private final S3Service s3Service;

	public ReportCreateResponse createReport(
		Organization authOrganization,
		ReportCreateRequest request,
		List<MultipartFile> images,
		List<MultipartFile> attachments
	) {
		final Report report = request.toEntity(authOrganization);
		setReportImages(images, report);
		setReportAttachments(attachments, report);
		return ReportCreateResponse.from(reportRepository.save(report));
	}


	public ReportCreateResponse updateReport(Long reportId, ReportCreateRequest request) {
		final Report report = reportRepository.findById(reportId)
			.orElseThrow(() -> new ReportException(ReportErrorCode.REPORT_NOT_FOUND));

		report.update(request.title(), request.content());

		reportRepository.save(report);
		return ReportCreateResponse.from(report);
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

