package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.register.CheckCertNoRequestDto;
import com.ukm.ssgb.dto.register.RegisterProfileRequestDto;
import com.ukm.ssgb.dto.register.RegisterRequestDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.service.CacheService;
import com.ukm.ssgb.service.CertNoService;
import com.ukm.ssgb.service.UserService;
import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;

@Service
@RequiredArgsConstructor
public class RegisterFacade {

    private final UserService userService;
    private final CacheService cacheService;
    private final CertNoService certNoService;


    public ExpiredAtDto sendCertMail(String emailToSend) {
        userService.validateEmailDuplication(emailToSend);
        return certNoService.sendCertNo(emailToSend);
    }

    public void checkCertNo(CheckCertNoRequestDto requestDto) {
        String userEmail = requestDto.getEmail();
        String certNoToCheck = requestDto.getCertNo();

        // TODO 인증 시도 횟수, 횟수 초과 시 잠금 기능 필요
        String certNo = cacheService.getString(LocalCacheType.CERT_NO, userEmail)
                .orElseThrow(() -> new ValidationException("인증메일 재발송을 통해 새로운 인증번호를 받아주세요."));
        if (!certNoToCheck.equals(certNo)) {
            throw new ValidationException("인증번호가 일치하지 않습니다.");
        }
    }

    public void register(RegisterRequestDto requestDto) {
        userService.registerUser(requestDto.getEmail(), requestDto.getPassword());
    }

    public void registerAdmin(RegisterRequestDto requestDto) {
        userService.registerAdmin(requestDto.getEmail(), requestDto.getPassword());
    }

    public void registerBusinessFile(MultipartFile businessFile) {
        userService.registerBusinessFile(getIdWithThrow(), businessFile);
    }

    public void registerProfile(RegisterProfileRequestDto requestDto) {
        userService.registerProfile(getIdWithThrow(), requestDto.getRegionId(), requestDto.getBusinessTypeId());
    }
}
