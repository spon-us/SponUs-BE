package com.sponus.sponusbe.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
