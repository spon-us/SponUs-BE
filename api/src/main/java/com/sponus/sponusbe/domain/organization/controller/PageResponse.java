package com.sponus.sponusbe.domain.organization.controller;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
	Integer totalPages,
	Integer currentPage,
	Integer pageSize,
	List<T> content
) {

	public static <T> PageResponse<T> of(Page<T> page) {
		return new PageResponse<>(
			page.getTotalPages(),
			page.getPageable().getPageNumber() + 1,
			page.getPageable().getPageSize(),
			page.getContent()
		);
	}
}
