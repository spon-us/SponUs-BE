package com.sponus.coredomain.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.organization.Organization;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByOrganizationOrderByCreatedAtDesc(Organization organization);
}
