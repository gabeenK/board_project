package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.password.PasswordCertMailRequestDto;
import com.ukm.ssgb.dto.password.PasswordCheckCertNoRequestDto;
import com.ukm.ssgb.dto.password.PasswordResetRequestDto;
import com.ukm.ssgb.facade.LoginFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/password")
public class PasswordController {

    private final LoginFacade loginFacade;

    @PostMapping("/mail")
    public ResponseEntity<ExpiredAtDto> sendCertMail(@Valid @RequestBody PasswordCertMailRequestDto requestDto) {
        ExpiredAtDto result = loginFacade.sendCertMail(requestDto.getEmail());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/mail/check")
    public ResponseEntity<Void> checkCertNo(@Valid @RequestBody PasswordCheckCertNoRequestDto requestDto) {
        loginFacade.checkCertNo(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> passwordReset(@Valid @RequestBody PasswordResetRequestDto requestDto) {
        loginFacade.resetPassword(requestDto);
        return ResponseEntity.ok().build();
    }
}
