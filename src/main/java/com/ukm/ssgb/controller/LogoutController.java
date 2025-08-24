package com.ukm.ssgb.controller;

import com.ukm.ssgb.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logout")
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping
    public ResponseEntity<Void> logout() {
        logoutService.logout();
        return ResponseEntity.ok().build();
    }
}
