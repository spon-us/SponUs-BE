package com.sponus.coredomain.domain.bookmark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByOrganization(Organization organization);
}
