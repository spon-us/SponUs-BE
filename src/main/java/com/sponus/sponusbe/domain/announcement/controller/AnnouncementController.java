package com.sponus.sponusbe.domain.announcement.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
@RestController
public class AnnouncementController {
	@GetMapping("/{announcementId}")
	public ApiResponse<?> getAnnouncement(@PathVariable Long announcementId) {
		return null;
	}

	@GetMapping("/recommend")
	public ApiResponse<?> getRecommendAnnouncement() {
		return null;
	}

	@GetMapping("/popular")
	public ApiResponse<?> getAnnouncement() {
		return null;
	}

	@GetMapping
	public ApiResponse<?> searchAnnouncement(@RequestParam(required = true) String search) {
		return null;
	}

	@PostMapping
	public ApiResponse<?> createAnnouncement() {
		return null;
	}

	@DeleteMapping("/{announcementId}")
	public ApiResponse<?> deleteAnnouncement(@PathVariable Long announcementId) {
		return null;
	}

	@PatchMapping("/{announcementId}")
	public ApiResponse<?> updateAnnouncement(@PathVariable Long announcementId) {
		return null;
	}

}
