package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.user.UserDetailDto;
import com.ukm.ssgb.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/info")
    public ResponseEntity<UserDetailDto> getUserInfo() {
        UserDetailDto result = userFacade.getUserInfo();
        return ResponseEntity.ok(result);
    }
}
