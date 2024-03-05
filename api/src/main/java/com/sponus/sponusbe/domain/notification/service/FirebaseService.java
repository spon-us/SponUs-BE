package com.sponus.sponusbe.domain.notification.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.notification.Notification;
import com.sponus.coredomain.domain.notification.repository.NotificationRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;
import com.sponus.coredomain.domain.report.Report;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;
import com.sponus.sponusbe.domain.notification.dto.request.FcmMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseService {

	@Value("${firebase.fcmUrl}")
	private String fcmUrl;

	@Value("${firebase.firebaseConfigPath}")
	private String firebaseConfigPath;

	@Value("${firebase.scope}")
	private String scope;

	private final ObjectMapper objectMapper;

	private final NotificationRepository notificationRepository;

	private final RedisUtil redisUtil;

	public void sendMessageTo(Organization targetOrganization, String title, String body, Announcement announcement,
		Propose propose, Report report) throws IOException {

		String token = getFcmToken(targetOrganization.getEmail());

		Notification notification = Notification.builder()
			.title(title)
			.body(body)
			.build();

		notification.setOrganization(targetOrganization);
		notification.setAnnouncement(announcement);
		notification.setPropose(propose);
		notification.setReport(report);

		String message = makeFcmMessage(token, notificationRepository.save(notification));

		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

		Request request = new Request.Builder()
			.url(fcmUrl)
			.post(requestBody)
			.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
			.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
			.build();

		log.info("Sending FCM request. URL: {}, Headers: {}, Body: {}", fcmUrl, request.headers(), message);

		Response response = client.newCall(request)
			.execute();

		// Response error
		String responseBodyString = response.body().string();
		log.info("Notification ResponseBody : {} ", responseBodyString);
		int codeIndex = responseBodyString.indexOf("\"code\":");
		int messageIndex = responseBodyString.indexOf("\"message\":");

		if (codeIndex != -1 && messageIndex != -1) {

			String responseErrorCode = responseBodyString.substring(codeIndex + "\"code\":".length(),
				responseBodyString.indexOf(',', codeIndex));
			responseErrorCode = responseErrorCode.trim();

			String responseErrorMessage = responseBodyString.substring(messageIndex + "\"message\":".length(),
				responseBodyString.indexOf(',', messageIndex));
			responseErrorMessage = responseErrorMessage.trim();

			log.info("[*]Error Code: " + responseErrorCode);
			log.info("[*]Error Message: " + responseErrorMessage);
		} else {
			// Response 정상
			log.info("[*]Error Code or Message not found");
		}
	}

	private String makeFcmMessage(String token, Notification notification) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.of(false,
			FcmMessage.Message.of(token,
				FcmMessage.NotificationSummary.from(notification)));

		log.info("Notification : {}", fcmMessage.message().notification().toString());

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String getAccessToken() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath)
			.getInputStream()).createScoped(List.of(scope));
		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	public String getFcmToken(String email) {
		return (String)redisUtil.get(email + "_fcm_token");
	}
}
