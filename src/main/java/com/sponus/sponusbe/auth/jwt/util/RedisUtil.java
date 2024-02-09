package com.sponus.sponusbe.auth.jwt.util;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	public void saveAsValue(String key, Object val, Long time, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, val, time, timeUnit);
	}

	public void appendToRecentlyViewedAnnouncement(String key, String newValue) {
		long RECENT_VIEWED_ANNOUNCEMENT_LIMIT = 20;

		log.info("[*] Newly Viewed Announcement: " + newValue);
		Object mostRecentlyViewedValue = redisTemplate.opsForList().index(key, 0);
		if (Objects.equals(mostRecentlyViewedValue, newValue)) {
			log.info("[*] Skip saving viewed history...");
			return;
		}
		if (Objects.equals(redisTemplate.opsForList().size(key), RECENT_VIEWED_ANNOUNCEMENT_LIMIT)) {
			log.info("[*] Recent Announcement Deque Full Capacity..");
			log.info("[*] Del Top()");
			redisTemplate.opsForList().rightPop(key);
		}
		redisTemplate.opsForList().leftPush(key, newValue);
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public List<Object> getList(String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}


	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}
}
