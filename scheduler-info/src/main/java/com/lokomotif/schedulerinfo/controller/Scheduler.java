package com.lokomotif.schedulerinfo.controller;

import com.lokomotif.schedulerinfo.service.GenerateInfo;
import com.lokomotif.schedulerinfo.service.SummaryService;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Scheduler {

    private final GenerateInfo generateInfoService;

    private final ScheduledExecutorService scheduler;

    private final SummaryService summaryService;

    public Scheduler(GenerateInfo generateInfoService, SummaryService summaryService) {
        this.generateInfoService = generateInfoService;
        this.summaryService = summaryService;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduleGeneration();
    }

    private void scheduleGeneration() {
        scheduler.scheduleAtFixedRate(generateInfoService::generateAndLogInfo, 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(summaryService::saveSummary,0,3600, TimeUnit.SECONDS);
    }
}