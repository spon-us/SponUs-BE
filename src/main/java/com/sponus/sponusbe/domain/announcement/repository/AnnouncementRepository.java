package com.sponus.sponusbe.domain.announcement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
