package com.example.db.metrics;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthIndicators {
    private final List<HealthIndicator> healthIndicators;
    public HealthIndicators(List<HealthIndicator> healthIndicators) {
        this.healthIndicators = healthIndicators;
    }
    public List<HealthIndicator> getHealthStatus() {
        return this.healthIndicators;
    }
}