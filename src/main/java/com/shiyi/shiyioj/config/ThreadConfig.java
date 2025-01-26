package com.shiyi.shiyioj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {
    static final int cores = Runtime.getRuntime().availableProcessors();
    static final int corePoolSize = cores;
    static final int maximumPoolSize = cores * 2;
    static final long keepAliveTime = 60;
    static final TimeUnit unit = TimeUnit.SECONDS;
    static final int queueCapacity = 1000;
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>(queueCapacity));
    }
}
