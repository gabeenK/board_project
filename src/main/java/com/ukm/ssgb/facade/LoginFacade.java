package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.LoginDto;
import com.ukm.ssgb.dto.LoginRequestDto;
import com.ukm.ssgb.dto.MacLoginRequestDto;
import com.ukm.ssgb.dto.password.PasswordCheckCertNoRequestDto;
import com.ukm.ssgb.dto.password.PasswordResetRequestDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.model.UserMac;
import com.ukm.ssgb.repository.UserMacRepository;
import com.ukm.ssgb.repository.UserRepository;
import com.ukm.ssgb.service.CacheService;
import com.ukm.ssgb.service.CertNoService;
import com.ukm.ssgb.service.JwtManager;
import com.ukm.ssgb.service.LoginService;
import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginFacade {

    private final LoginService loginService;
    private final JwtManager jwtManager;
    private final CacheService cacheService;

    private final CertNoService certNoService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMacRepository userMacRepository;


    public LoginDto login(LoginRequestDto requestDto) {
        User loginUser = loginService.login(requestDto.getEmail(), requestDto.getPassword());
        String accessToken = jwtManager.createAccessToken(loginUser.getUserId());
        String refreshToken = jwtManager.createRefreshToken(loginUser.getUserId());
        return new LoginDto(accessToken, refreshToken);
    }

    public ExpiredAtDto sendCertMail(String emailToSend) {
        return certNoService.sendCertNo(emailToSend);
    }

    public void checkCertNo(PasswordCheckCertNoRequestDto requestDto) {
        String userEmail = requestDto.getEmail();
        String certNoToCheck = requestDto.getCertNo();

        // TODO 인증 시도 횟수, 횟수 초과 시 잠금 기능 필요
        String certNo = cacheService.getString(LocalCacheType.CERT_NO, userEmail)
                .orElseThrow(() -> new ValidationException("인증메일 재발송을 통해 새로운 인증번호를 받아주세요."));
        if (!certNoToCheck.equals(certNo)) {
            throw new ValidationException("인증번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void resetPassword(PasswordResetRequestDto requestDto) {
        if (!requestDto.getNewPassword().equals(requestDto.getConfirmPassword())) {
            throw new ValidationException("비밀번호가 다릅니다.");
        }
        User user = userRepository.findByEmailAndDeletedFalse(requestDto.getEmail()).orElseThrow();
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    @Transactional
    public LoginDto macLogin(MacLoginRequestDto requestDto) {
        String macAddress = requestDto.getPassword();
        UserMac userMac = userMacRepository.findByMacAddress(macAddress)
                .orElseThrow(() -> new ValidationException("등록되지 않은 맥주소입니다. [%s]".formatted(macAddress)));
        User loginUser = userRepository.findById(userMac.getUserId()).orElseThrow();

        loginUser.login();

        String accessToken = jwtManager.createAccessToken(loginUser.getUserId());
        String refreshToken = jwtManager.createRefreshToken(loginUser.getUserId());
        return new LoginDto(accessToken, refreshToken);
    }
}
