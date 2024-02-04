package com.sponus.sponusbe.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
