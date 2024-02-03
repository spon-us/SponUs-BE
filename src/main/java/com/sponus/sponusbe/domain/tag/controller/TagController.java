package com.sponus.sponusbe.domain.tag.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.tag.dto.request.TagCreateRequest;
import com.sponus.sponusbe.domain.tag.dto.resposne.TagCreateResponse;
import com.sponus.sponusbe.domain.tag.service.TagService;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
@RestController
public class TagController {
	private final TagService tagService;

	@PostMapping
	public ApiResponse<TagCreateResponse> createTag(@AuthOrganization Organization organization,
		@RequestBody TagCreateRequest request) {
		return ApiResponse.onSuccess(tagService.createTag(organization.getId(), request));
	}

	@DeleteMapping("/{tagId}")
	public ApiResponse<Void> deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);

		return ApiResponse.onSuccess(null);
	}
}
