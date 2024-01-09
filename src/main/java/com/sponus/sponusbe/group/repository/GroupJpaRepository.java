package com.sponus.sponusbe.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.group.entity.Group;

public interface GroupJpaRepository extends JpaRepository<Group, Long> {

	Optional<Group> findGroupByEmail(String email);
}
