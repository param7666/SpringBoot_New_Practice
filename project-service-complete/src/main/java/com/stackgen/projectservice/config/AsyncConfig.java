package com.stackgen.projectservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean("generationTaskExecutor")
    public ThreadPoolTaskExecutor generationTaskExecutor(
            @Value("${generation.executor.core-pool-size:4}") int corePoolSize,
            @Value("${generation.executor.max-pool-size:8}") int maxPoolSize,
            @Value("${generation.executor.queue-capacity:50}") int queueCapacity) {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("generation-");
        executor.initialize();
        return executor;
    }
}
