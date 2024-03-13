package com.sponus.coreinfraredis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories("com.sponus.coreinfraredis")
public class CoreRedisConfig {
}
