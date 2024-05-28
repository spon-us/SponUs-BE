package com.sponus.coredomain.domain.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	List<Bookmark> findByOrganization(Organization organization);

	Optional<Bookmark> findByOrganizationAndTarget(Organization organization, Organization target);

	List<Bookmark> findByOrganizationOrderByCreatedAtDesc(Organization organization);
}
