package com.sponus.sponusbe.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.report.Report;
import com.sponus.coredomain.domain.report.repository.ReportRepository;
import com.sponus.sponusbe.domain.report.dto.response.ReportGetResponse;
import com.sponus.sponusbe.domain.report.exception.ReportErrorCode;
import com.sponus.sponusbe.domain.report.exception.ReportException;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportQueryService {

	private final ReportRepository reportRepository;

	public ReportGetResponse readReport(Long id) {
		Report report = reportRepository.findById(id)
			.orElseThrow(() -> new ReportException(ReportErrorCode.REPORT_NOT_FOUND));
		return ReportGetResponse.from(report);
	}
}
