package com.sponus.sponusbe.domain.organization.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageCondition {
	private static final Integer DEFAULT_PAGE = 1;
	private static final Integer DEFAULT_SIZE = 10;

	private Integer page;
	private Integer size;

	public PageCondition(Integer page, Integer size) {
		this.page = isValidPage(page) ? page : DEFAULT_PAGE;
		this.size = isValidSize(size) ? size : DEFAULT_SIZE;
	}

	private Boolean isValidPage(Integer page) {
		return page != null && page > 0;
	}

	private Boolean isValidSize(Integer size) {
		return size != null && size > 0;
	}
}
