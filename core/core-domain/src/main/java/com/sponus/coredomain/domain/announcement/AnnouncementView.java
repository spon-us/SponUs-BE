package com.sponus.coredomain.domain.announcement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("announcementView")
public class AnnouncementView {
	@Id
	private String announcementId;
	@Builder.Default
	private Set<String> organizationIds = new HashSet<>();

	@TimeToLive
	@Builder.Default
	private Long expiration = Duration.between(LocalDateTime.now(), LocalDate.now().plusDays(1).atStartOfDay())
		.getSeconds();
}
