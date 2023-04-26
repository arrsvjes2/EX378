package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class ReadinessHealthResource {

    private final String HEALTH_CHECK_NAME = "Readiness";
    public HealthCheckResponse call() {
        return ++counter >= 10
            ? HealthCheckResponse.up(HEALTH_CHECK_NAME)
            : HealthCheckResponse.down(HEALTH_CHECK_NAME);
    }
}
