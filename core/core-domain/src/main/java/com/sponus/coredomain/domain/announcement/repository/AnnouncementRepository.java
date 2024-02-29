package com.sponus.coredomain.domain.announcement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	List<Announcement> findByWriterIdOrderByCreatedAtDesc(Long writerId);

	List<Announcement> findByTitleContains(String title);

	// List<Announcement> findByStatus(AnnouncementStatus status);

	List<Announcement> findByCategoryAndTypeOrderByCreatedAtDesc(AnnouncementCategory category, AnnouncementType type);

	List<Announcement> findByCategoryOrderByCreatedAtDesc(AnnouncementCategory category);

	List<Announcement> findByTypeOrderByCreatedAtDesc(AnnouncementType type);

	@Query("SELECT a FROM Announcement a WHERE a.status='OPENED' ORDER BY a.viewCount DESC LIMIT 10")
	List<Announcement> findTop10OrderByViewCountDesc();

	@Query("SELECT a FROM Announcement a WHERE a.status='OPENED' ORDER BY RANDOM() LIMIT 10")
	List<Announcement> findOrderByRandom();
}
