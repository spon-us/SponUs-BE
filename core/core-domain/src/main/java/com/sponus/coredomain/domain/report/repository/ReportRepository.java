package com.sponus.coredomain.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
