package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.LoginDto;
import com.ukm.ssgb.dto.LoginRequestDto;
import com.ukm.ssgb.dto.MacLoginRequestDto;
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
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginFacade loginFacade;

    @PostMapping
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginDto result = loginFacade.login(requestDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/mac")
    public ResponseEntity<LoginDto> macLogin(@Valid @RequestBody MacLoginRequestDto requestDto) {
        LoginDto result = loginFacade.macLogin(requestDto);
        return ResponseEntity.ok(result);
    }
}
