package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.register.CertMailRequestDto;
import com.ukm.ssgb.dto.register.CheckCertNoRequestDto;
import com.ukm.ssgb.dto.register.RegisterProfileRequestDto;
import com.ukm.ssgb.dto.register.RegisterRequestDto;
import com.ukm.ssgb.facade.RegisterFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegisterController {

    private final RegisterFacade registerFacade;

    @PostMapping("/mail")
    public ResponseEntity<ExpiredAtDto> sendCertMail(@Valid @RequestBody CertMailRequestDto requestDto) {
        return ResponseEntity.ok(registerFacade.sendCertMail(requestDto.getEmail()));
    }

    @PostMapping("/mail/check")
    public ResponseEntity<Void> checkCertNo(@Valid @RequestBody CheckCertNoRequestDto requestDto) {
        registerFacade.checkCertNo(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        requestDto.validate();
        registerFacade.register(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> registerAdmin(@Valid @RequestBody RegisterRequestDto requestDto) {
        requestDto.validate();
        registerFacade.registerAdmin(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/businessFile")
    public ResponseEntity<Void> registerBusinessFile(@RequestPart(value = "businessFile") MultipartFile businessFile) {
        registerFacade.registerBusinessFile(businessFile);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> registerProfile(@Valid @RequestBody RegisterProfileRequestDto requestDto) {
        registerFacade.registerProfile(requestDto);
        return ResponseEntity.ok().build();
    }
}
