package com.ukm.ssgb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukm.ssgb.constant.ServiceConstants;
import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.ukm.ssgb.util.RandomNumberGenerator.generateCertNoString;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebHookService implements CertNoService {

    private static final String WEBHOOK_PAYLOAD_KEY = "text";
    private static final String CERT_NO_MESSAGE = "사용자: %s%n인증번호: %s";

    @Value("${slack.webhook.url.sotalk.scheduler}")
    private String schedulerUrl;

    @Value("${slack.webhook.url.sotalk.certNo}")
    private String certNoUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CacheService cacheService;


    public void sendMessage(String message) {
        HttpEntity<String> request = createRequest(message);
        ResponseEntity<String> response = restTemplate.postForEntity(schedulerUrl, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Message sent successfully to Slack");
        } else {
            log.error("Failed to send message to Slack: {}", response.getStatusCode());
        }
    }

    @Override
    public ExpiredAtDto sendCertNo(String email) {
        String certNo = generateCertNoString();
        cacheService.setString(LocalCacheType.CERT_NO, email, certNo);

        String message = String.format(CERT_NO_MESSAGE, email, certNo);
        HttpEntity<String> request = createRequest(message);
        ResponseEntity<String> response = restTemplate.postForEntity(certNoUrl, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("CertNo sent successfully to Slack");
        } else {
            log.error("Failed to send CertNo to Slack: {}", response.getStatusCode());
        }

        return new ExpiredAtDto(System.currentTimeMillis() + ServiceConstants.CERT_NO_VALIDATE_TIME);
    }

    @SneakyThrows
    private HttpEntity<String> createRequest(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = objectMapper.writeValueAsString(Map.of(WEBHOOK_PAYLOAD_KEY, message));

        return new HttpEntity<>(body, headers);
    }
}
