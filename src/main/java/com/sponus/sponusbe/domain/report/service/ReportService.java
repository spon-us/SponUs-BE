package com.sponus.sponusbe.domain.report.service;

import org.springframework.stereotype.Service;

import com.sponus.sponusbe.domain.report.dto.ReportRequest;
import com.sponus.sponusbe.domain.report.dto.ReportResponse;
import com.sponus.sponusbe.domain.report.entity.Report;
import com.sponus.sponusbe.domain.report.exception.ReportErrorCode;
import com.sponus.sponusbe.domain.report.exception.ReportException;
import com.sponus.sponusbe.domain.report.repository.ReportRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ReportService {
	private final ReportRepository reportRepository;

	public ReportResponse createReport(ReportRequest request) {
		final Report report = reportRepository.save(request.toEntity());
		return ReportResponse.from(report);
	}

	public ReportResponse updateReport(Long reportId, ReportRequest request) {
		final Report report = reportRepository.findById(reportId)
			.orElseThrow(() -> new ReportException(ReportErrorCode.REPORT_NOT_FOUND));

		report.update(request.title(), request.content());
		// TODO : 첨부파일 수정 추가

		reportRepository.save(report);
		return ReportResponse.from(report);
	}

}

