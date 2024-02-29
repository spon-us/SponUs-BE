package com.sponus.sponusbe.global.config;

import com.sponus.sponusbe.domain.announcement.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SchedulerConfig {
    private final AnnouncementService announcementService;

    @Scheduled(fixedDelay = 1000L * 60)
    public void updateAllViewedAnnouncementViewCount() {
        announcementService.updateAllViewedAnnouncementViewCount();
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void resetAllAnnouncementViewCount() {
        announcementService.resetAllAnnouncementViewCount();
    }
}
