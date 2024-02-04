package com.sponus.sponusbe.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
