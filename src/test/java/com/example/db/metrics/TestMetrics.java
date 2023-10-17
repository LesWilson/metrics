package com.example.db.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMetrics {

    @Test
    void test1() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Counter counter = Counter
                .builder("instance")
                .description("indicates instance count of the object")
                .tags("dev", "performance")
                .register(registry);

        counter.increment(2.0);

        assertTrue(counter.count() == 2);

        counter.increment(-1);

        assertTrue(counter.count() == 1);
    }
}
