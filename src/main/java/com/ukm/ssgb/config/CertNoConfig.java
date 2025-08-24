package com.ukm.ssgb.config;

import com.ukm.ssgb.service.CertNoService;
import com.ukm.ssgb.service.EmailService;
import com.ukm.ssgb.service.WebHookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class CertNoConfig {

    @Bean
    @Primary
    @Profile("!dev")
    public CertNoService emailCertNoService(EmailService emailService) {
        return emailService;
    }

    @Bean
    @Primary
    @Profile("dev")
    public CertNoService webhookCertNoService(WebHookService webhookService) {
        return webhookService;
    }
}
