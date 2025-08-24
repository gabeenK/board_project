package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.usermanagement.*;
import com.ukm.ssgb.facade.UserManagementFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user")
public class UserManagementController {

    private final UserManagementFacade userManagementFacade;

    @GetMapping
    public ResponseEntity<PageContentsDto<List<GetUsersDto>>> getUsers(@Valid GetUsersRequestDto requestDto) {
        return ResponseEntity.ok(userManagementFacade.getUsers(requestDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody @Valid DeleteUsersRequestDto requestDto) {
        userManagementFacade.deleteUsers(requestDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/approval")
    public ResponseEntity<Void> approveUsers(@RequestBody @Valid ApproveUsersRequestDto requestDto) {
        userManagementFacade.approveUsers(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserDto> getUser(@PathVariable("userId") Long userId) {
        GetUserDto result = userManagementFacade.getUser(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserDto> updateUser(@PathVariable("userId") Long userId,
                                           @ModelAttribute UpdateUserRequestDto requestDto) {
        UpdateUserDto result = userManagementFacade.updateUser(userId, requestDto);
        return ResponseEntity.ok(result);
    }
}
