package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.LoginDto;
import com.ukm.ssgb.dto.TokenRefreshRequestDto;
import com.ukm.ssgb.facade.TokenFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenFacade tokenFacade;

    @PostMapping("/refresh")
    public ResponseEntity<LoginDto> refresh(@Valid @RequestBody TokenRefreshRequestDto requestDto) {
        LoginDto result = tokenFacade.refresh(requestDto.getRefreshToken());
        return ResponseEntity.ok(result);
    }
}
