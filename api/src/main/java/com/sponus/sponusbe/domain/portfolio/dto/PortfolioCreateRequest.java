package com.sponus.sponusbe.domain.portfolio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record PortfolioCreateRequest(
	//TODO: DateTimeFormat 검사.
	@NotNull(message = "필수 입력란입니다.")
	LocalDate startDate,
	@NotNull(message = "필수 입력란입니다.")
	LocalDate endDate,
	String description
) {
}
