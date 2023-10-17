package com.example.db.scheduled;

import com.example.db.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IncrementCounters {

    final
    MetricsService metricsService;

    public IncrementCounters(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Scheduled(fixedDelay = 3000)
    public void scheduled() {
        int i = new Random().nextInt(3);
        switch (i) {
            case 0:
                metricsService.messageSuccess();
                break;
            case 1:
                metricsService.messageFailed();
                break;
            case 2:
                metricsService.messageRetried();
        }
    }
}
