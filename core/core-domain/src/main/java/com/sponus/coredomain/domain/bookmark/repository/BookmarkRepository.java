package com.sponus.coredomain.domain.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.bookmark.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByOrganizationAndAnnouncement(Organization organization, Announcement announcement);

	List<Bookmark> findByOrganizationOrderByCreatedAtDesc(Organization organization);

	List<Bookmark> findByOrganizationOrderByAnnouncementViewCountDesc(Organization organization);

	List<Bookmark> findByOrganizationOrderBySaveCountDesc(Organization organization);

}
