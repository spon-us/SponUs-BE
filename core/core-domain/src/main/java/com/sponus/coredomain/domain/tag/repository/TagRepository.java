package com.sponus.coredomain.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.tag.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
