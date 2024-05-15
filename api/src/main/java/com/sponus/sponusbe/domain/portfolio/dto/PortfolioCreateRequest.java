package com.sponus.sponusbe.domain.portfolio.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record PortfolioCreateRequest(
	LocalDate startDate,
	LocalDate endDate,
	String description,
	List<MultipartFile>
	) {
}
