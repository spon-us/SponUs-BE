package com.sponus.sponusbe.domain.portfolio.dto;

import java.time.LocalDate;

public record PortfolioUpdateRequest(
	//TODO: DateTimeFormat 검사.
	LocalDate startDate,
	LocalDate endDate,
	String description
) {
}
