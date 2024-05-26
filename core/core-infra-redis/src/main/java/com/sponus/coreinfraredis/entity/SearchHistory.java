package com.sponus.coreinfraredis.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("searchHistory")
public class SearchHistory {

	@Id
	private Long organizationId;

	@Builder.Default
	private Set<String> keywords = new LinkedHashSet<>();
}
