package com.sponus.sponusbe.domain.portfolio.dto;

import java.time.LocalDate;

public record PortfolioUpdateRequest(
	LocalDate startDate,
	LocalDate endDate,
	String description
) {
}
