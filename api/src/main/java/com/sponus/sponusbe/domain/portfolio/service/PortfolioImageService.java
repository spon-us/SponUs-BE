package com.sponus.sponusbe.domain.portfolio.service;

import org.springframework.stereotype.Service;

import com.sponus.coredomain.domain.portfolio.repository.PortfolioImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioImageService {
	private final PortfolioImageRepository portfolioImageRepository;

}
