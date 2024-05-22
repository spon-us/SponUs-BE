package com.sponus.sponusbe.domain.portfolio.dto;

import java.util.List;

public record PortfolioCreateResponse(
	Long portfolioId,
	List<Long> portfolioImageIds
) {
}
