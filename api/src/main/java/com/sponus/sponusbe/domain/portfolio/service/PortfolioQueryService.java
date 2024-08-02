package com.sponus.sponusbe.domain.portfolio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.portfolio.Portfolio;
import com.sponus.coredomain.domain.portfolio.repository.PortfolioRepository;
import com.sponus.coredomain.domain.portfolio.repository.conditions.PortfolioSearchParam;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioGetResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageGetResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PortfolioQueryService {

	private final PortfolioRepository portfolioRepository;

	public Page<PortfolioGetResponse> getPortfolios(PortfolioSearchParam portfolioSearchParam, Pageable pageable) {
		Page<Portfolio> queryResult = portfolioRepository.findAllByConditions(portfolioSearchParam, pageable);
		return queryResult.map(portfolio -> {
			List<PortfolioImageGetResponse> portfolioImageGetResponses = portfolio.getPortfolioImages().stream()
				.map(image -> new PortfolioImageGetResponse(image.getId(), image.getUrl(), image.getOrder()))
				.toList();
			return PortfolioGetResponse.from(portfolio, portfolioImageGetResponses);
		});
	}
}
