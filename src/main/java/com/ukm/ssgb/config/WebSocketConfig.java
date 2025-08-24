package com.ukm.ssgb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableScheduling // TODO 웹소켓 연결 모니터링을 위해 임시 활성화
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketMessageBrokerStats monitor;

    public WebSocketConfig(@Lazy WebSocketMessageBrokerStats monitor) {
        this.monitor = monitor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect");
    }

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.MINUTES)
    public void monitoring() {
        log.info("WS STATUS: {}", monitor.getWebSocketSessionStatsInfo());
    }
}

