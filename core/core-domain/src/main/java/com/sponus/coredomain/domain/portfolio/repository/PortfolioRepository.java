package com.sponus.coredomain.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.portfolio.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
