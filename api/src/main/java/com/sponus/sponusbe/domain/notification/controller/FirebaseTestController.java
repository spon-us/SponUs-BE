package com.sponus.sponusbe.domain.notification.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.notification.dto.request.NotificationTestRequest;
import com.sponus.sponusbe.domain.notification.service.FirebaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/notification")
@RestController
public class FirebaseTestController {

	private final FirebaseService firebaseService;

	@PostMapping("/fcm")
	public String testNotification(@RequestBody NotificationTestRequest request,
		@AuthOrganization Organization organization) throws IOException {
		firebaseService.sendMessageTo(organization, request.title(), request.body(), null, null, null);
		return "Notification test is successful !";
	}
}
