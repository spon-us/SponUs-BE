package com.sponus.sponusbe.domain.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkGetResponse;
import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;
import com.sponus.sponusbe.domain.organization.entity.Organization;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByOrganizationAndAnnouncement(Organization organization, Announcement announcement);
	List<Bookmark> findByOrganizationOrderByCreatedAtDesc(Organization organization);
	List<Bookmark> findByOrganizationOrderBySaveCountDesc(Organization organization);

}