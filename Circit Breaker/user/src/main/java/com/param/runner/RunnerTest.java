package com.param.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Component
public class RunnerTest {

    
	@Bean
    CommandLineRunner printConfig(CircuitBreakerRegistry registry) {
        return args -> {
            var cfg = registry.circuitBreaker("providerCB").getCircuitBreakerConfig();
            System.out.println("windowSize=" + cfg.getSlidingWindowSize()
                    + ", minCalls=" + cfg.getMinimumNumberOfCalls()
                    + ", threshold=" + cfg.getFailureRateThreshold()
                    + ", waitOpen=" + cfg.getWaitIntervalFunctionInOpenState().apply(1) + "ms"
                    + ", halfOpenCalls=" + cfg.getPermittedNumberOfCallsInHalfOpenState());
        };
    }
    
}
