package com.sponus.coredomain.domain.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sponus.coredomain.domain.portfolio.Portfolio;
import com.sponus.coredomain.domain.portfolio.repository.conditions.PortfolioSearchParam;

public interface PortfolioCustomRepository {
	Page<Portfolio> findAllByConditions(PortfolioSearchParam portfolioSearchParam, Pageable pageable);
}
