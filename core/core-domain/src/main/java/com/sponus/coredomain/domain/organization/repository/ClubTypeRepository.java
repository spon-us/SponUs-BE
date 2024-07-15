package com.sponus.coredomain.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.organization.club.ClubType;

public interface ClubTypeRepository extends JpaRepository<ClubType, Integer> {
}
