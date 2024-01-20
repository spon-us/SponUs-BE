package com.sponus.sponusbe.domain.propose.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.propose.entity.Propose;

public interface ProposeRepository extends JpaRepository<Propose, Long> {
}
