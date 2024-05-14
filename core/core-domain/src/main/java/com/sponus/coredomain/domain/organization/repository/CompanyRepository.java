package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
