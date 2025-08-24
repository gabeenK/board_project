package com.ukm.ssgb.service;

import com.ukm.ssgb.constant.ServiceConstants;
import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ukm.ssgb.util.RandomNumberGenerator.generateCertNoString;

@Service
@RequiredArgsConstructor
public class EmailService implements CertNoService {

    private final CacheService cacheService;

    @Override
    public ExpiredAtDto sendCertNo(String email) {
        String certNo = generateCertNoString();
        cacheService.setString(LocalCacheType.CERT_NO, email, certNo);
        // TODO 메일 전송
        return new ExpiredAtDto(System.currentTimeMillis() + ServiceConstants.CERT_NO_VALIDATE_TIME);
    }
}
