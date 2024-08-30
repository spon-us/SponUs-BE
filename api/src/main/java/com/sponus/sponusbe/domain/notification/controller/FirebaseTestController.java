package com.sponus.sponusbe.domain.notification.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrafirebase.FirebaseUtil;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.notification.dto.request.NotificationTestRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(NOTIFICATION_URI)
@RestController
public class FirebaseTestController {

	private final FirebaseUtil firebaseUtil;

	@PostMapping("/fcm")
	public String testNotification(@RequestBody NotificationTestRequest request,
		@AuthOrganization Organization organization) throws IOException {
		firebaseUtil.sendMessageTo(organization, request.title(), request.body(), null);
		return "Notification test is successful !";
	}
}
