package com.sponus.sponusbe.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.dto.ReportCreateResponse;
import com.sponus.sponusbe.domain.report.dto.ReportGetResponse;
import com.sponus.sponusbe.domain.report.entity.Report;
import com.sponus.sponusbe.domain.report.exception.ReportErrorCode;
import com.sponus.sponusbe.domain.report.exception.ReportException;
import com.sponus.sponusbe.domain.report.repository.ReportRepository;

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
