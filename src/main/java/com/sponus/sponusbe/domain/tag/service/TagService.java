package com.sponus.sponusbe.domain.tag.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;
import static com.sponus.sponusbe.domain.tag.exception.TagErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;
import com.sponus.sponusbe.domain.tag.dto.request.TagCreateRequest;
import com.sponus.sponusbe.domain.tag.dto.request.TagUpdateRequest;
import com.sponus.sponusbe.domain.tag.dto.resposne.TagCreateResponse;
import com.sponus.sponusbe.domain.tag.entity.Tag;
import com.sponus.sponusbe.domain.tag.exception.TagException;
import com.sponus.sponusbe.domain.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TagService {
	private final OrganizationRepository organizationRepository;
	private final TagRepository tagRepository;

	@Transactional
	public TagCreateResponse createTag(Long organizationId, TagCreateRequest request) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));

		Tag tag = request.toEntity(organization);
		organization.getTags().add(tag);
		tag = tagRepository.save(tag);

		return new TagCreateResponse(tag.getId(), organization.getId());
	}

	@Transactional
	public void updateTag(Long tagId, TagUpdateRequest request) {
		Tag tag = tagRepository.findById(tagId)
			.orElseThrow(() -> new TagException(TAG_NOT_FOUND));
		tag.update(request);
	}
}
