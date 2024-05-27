package com.sponus.coredomain.domain.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.portfolio.PortfolioImage;

public interface PortfolioImageRepository extends JpaRepository<PortfolioImage, Long> {
}
