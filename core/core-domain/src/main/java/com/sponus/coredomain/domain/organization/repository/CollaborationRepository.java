package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.company.CollaborationType;

public interface CollaborationRepository extends JpaRepository<CollaborationType, Long> {
}
