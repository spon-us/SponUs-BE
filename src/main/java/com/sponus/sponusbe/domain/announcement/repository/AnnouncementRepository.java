package com.sponus.sponusbe.domain.announcement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	List<Announcement> findByTitleContains(String title);

	List<Announcement> findByStatus(AnnouncementStatus status);

	List<Announcement> findByCategoryAndType(AnnouncementCategory category, AnnouncementType type);

	List<Announcement> findByCategory(AnnouncementCategory category);

	List<Announcement> findByType(AnnouncementType type);

	@Query("SELECT a FROM Announcement a WHERE a.status='OPENED' ORDER BY a.viewCount DESC LIMIT 10")
	List<Announcement> findTop10OrderByViewCountDesc();

	@Query("SELECT a FROM Announcement a WHERE a.status='OPENED' ORDER BY RANDOM() LIMIT 10")
	List<Announcement> findOrderByRandom();
}
