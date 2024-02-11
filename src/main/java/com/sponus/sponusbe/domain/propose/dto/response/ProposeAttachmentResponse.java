package com.sponus.sponusbe.domain.propose.dto.response;

import com.sponus.sponusbe.domain.propose.entity.ProposeAttachment;

import lombok.Builder;

@Builder
public record ProposeAttachmentResponse(
	Long id,
	String name,
	String url
) {
	public static ProposeAttachmentResponse from(ProposeAttachment attachment) {
		return ProposeAttachmentResponse.builder()
			.id(attachment.getId())
			.name(attachment.getName())
			.url(attachment.getUrl())
			.build();
	}
}
