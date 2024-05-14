package com.sponus;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("com.sponus.coredomain")
@EnableJpaRepositories("com.sponus.coredomain")
@EnableJpaAuditing
public class CoreDomainConfig {
}
