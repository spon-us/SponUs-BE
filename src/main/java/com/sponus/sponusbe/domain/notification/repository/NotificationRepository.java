package com.sponus.sponusbe.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.domain.notification.entity.Notification;
import com.sponus.sponusbe.domain.organization.entity.Organization;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByOrganization(Organization organization);
}
