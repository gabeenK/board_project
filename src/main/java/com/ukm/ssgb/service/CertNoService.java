package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.ExpiredAtDto;

public interface CertNoService {
    ExpiredAtDto sendCertNo(String email);
}
