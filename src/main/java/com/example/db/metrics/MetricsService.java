package com.example.db.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.mail.MailHealthIndicator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MetricsService {
    private Counter successCounter;
    private Counter failCounter;
    private Counter retryCounter;
    private Counter processedCounter;
    private HealthIndicators healthIndicators;
    private JavaMailSenderImpl mailSender;
    private final MailHealthIndicator mailHealthIndicator;
    private AtomicInteger mailStatus = null;

    public MetricsService(MeterRegistry meterRegistry, HealthIndicators healthIndicators, JavaMailSenderImpl mailSender) {
        this.healthIndicators = healthIndicators;
        this.mailSender = mailSender;
        mailHealthIndicator = new MailHealthIndicator(mailSender);
        successCounter = meterRegistry.counter("total.success");
        failCounter = meterRegistry.counter("total.fail");
        processedCounter = meterRegistry.counter("total.processed");
        retryCounter = meterRegistry.counter("total.retries");
//        List<HealthIndicator> healthStatus = healthIndicators.getHealthStatus();
//        System.out.println("list size:"+healthStatus.size());
//        healthStatus.stream().forEach(it -> System.out.println("Health:"+it.getClass() + ":" + it.getHealth(true)));
        mailStatus = meterRegistry.gauge("status.of.mail.gateway",new AtomicInteger(0));
    }
    public void messageFailed() {
        failCounter.increment();
        processedCounter.increment();
        log.warn("Message failed");
        Health health = mailHealthIndicator.getHealth(true);
        System.out.println("health:"+ health);
        Status healthStatus = health.getStatus();
        System.out.println(healthStatus);
        if(healthStatus.equals(Status.DOWN)) {
            mailStatus.set(1);
        } else {
            mailStatus.set(0);
        }
    }
    public void messageSuccess() {
        successCounter.increment();
        processedCounter.increment();
        log.info("Message Success");
//        List<HealthIndicator> healthStatus = healthIndicators.getHealthStatus();
//        System.out.println("list size:"+healthStatus.size());
//        healthStatus.stream().forEach(it -> System.out.println("Health:"+it.getClass() + ":" + it.getHealth(true)));

    }
    public void messageRetried() {
        retryCounter.increment();
        log.error("Message retried");
    }


}

/**
 private AtomicInteger activeUsers = null;
 activeUsers = meterRegistry.gauge("number.of.active.users",new AtomicInteger(0));
 */