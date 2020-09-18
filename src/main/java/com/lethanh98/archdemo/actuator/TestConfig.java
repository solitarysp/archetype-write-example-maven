package com.lethanh98.archdemo.actuator;

import java.time.Duration;

public interface TestConfig {

    default String prefix() {
        return "prometheus";
    }

    /**
     * @return {@code true} if meter descriptions should be sent to Prometheus.
     * Turn this off to minimize the amount of data sent on each scrape.
     */
    default boolean descriptions() {
        return true;
    }

    /**
     * @return The step size to use in computing windowed statistics like max. The default is 1 minute.
     * To get the most out of these statistics, align the step interval to be close to your scrape interval.
     */
    default Duration step() {
        return Duration.ofMinutes(1);
    }
}
