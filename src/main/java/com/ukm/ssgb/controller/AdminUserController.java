package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.user.UserInfoDto;
import com.ukm.ssgb.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user")
public class AdminUserController {

    private final UserFacade userFacade;

    @GetMapping("/{userId}/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") Long userId) {
        UserInfoDto result = userFacade.getUserInfoById(userId);
        return ResponseEntity.ok(result);
    }
}
