package com.sponus.sponusbe.domain.organization.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageResponse<T>(
	Integer totalPages,
	Integer currentPage,
	Integer pageSize,
	List<T> content
) {

	public static <T> PageResponse<T> of(Page<T> page) {
		return PageResponse.<T>builder()
			.totalPages(page.getTotalPages())
			.currentPage(page.getPageable().getPageNumber() + 1)
			.pageSize(page.getPageable().getPageSize())
			.content(page.getContent())
			.build();
	}
}
