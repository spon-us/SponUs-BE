package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.company.CompanyType;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
}
