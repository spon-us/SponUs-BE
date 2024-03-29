package com.sponus.sponusbe.domain.tag.service;

import static com.sponus.sponusbe.domain.tag.exception.TagErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.tag.Tag;
import com.sponus.coredomain.domain.tag.repository.TagRepository;
import com.sponus.sponusbe.domain.tag.dto.TagGetResponse;
import com.sponus.sponusbe.domain.tag.exception.TagException;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TagQueryService {

	private final TagRepository tagRepository;

	public TagGetResponse getTag(Long tagId) {
		Tag tag = tagRepository.findById(tagId)
			.orElseThrow(() -> new TagException(TAG_NOT_FOUND));

		return TagGetResponse.from(tag);
	}
}
