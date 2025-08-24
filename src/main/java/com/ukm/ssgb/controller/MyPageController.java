package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.ExpiredAtDto;
import com.ukm.ssgb.dto.board.BoardListDto;
import com.ukm.ssgb.dto.myPage.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.facade.BoardBookmarkFacade;
import com.ukm.ssgb.facade.MyPageFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myPage")
public class MyPageController {

    private final MyPageFacade myPageFacade;
    private final BoardBookmarkFacade boardBookmarkFacade;

    @GetMapping(value = "/profile")
    public ResponseEntity<MyPageProfileDto> getProfile() {
        MyPageProfileDto result = myPageFacade.getProfile();
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyPageProfileImageDto> updateProfileImage(@Valid @ModelAttribute MyPageProfileImageRequestDto requestDto) {
        MyPageProfileImageDto result = myPageFacade.updateProfileImage(requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/profile/image")
    public ResponseEntity<Void> deleteProfileImage() {
        myPageFacade.deleteProfileImage();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/profile/nickname")
    public ResponseEntity<MyPageNicknameDto> nickname() {
        MyPageNicknameDto result = myPageFacade.updateNickname();
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/profile/region")
    public ResponseEntity<Void> region(@Valid @RequestBody MyPageProfileRegionRequestDto requestDto) {
        myPageFacade.updateRegion(requestDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/profile/businessType")
    public ResponseEntity<Void> businessType(@Valid @RequestBody MyPageProfileBusinessTypeRequestDto requestDto) {
        myPageFacade.updateBusinessType(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/mail")
    public ResponseEntity<ExpiredAtDto> passwordMail() {
        return ResponseEntity.ok(myPageFacade.sendPasswordMail());
    }

    @PostMapping("/password/mail/check")
    public ResponseEntity<Void> passwordMailCheck(@Valid @RequestBody PasswordMailCheckRequestDto requestDto) {
        myPageFacade.checkCertNo(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> passwordReset(@Valid @RequestBody PasswordResetRequestDto requestDto) {
        myPageFacade.resetPassword(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookmark")
    public ResponseEntity<PageContentsDto<List<BoardListDto>>> bookmarkList(@Valid PageRequestDto pageRequestDto) {
        PageContentsDto<List<BoardListDto>> result = boardBookmarkFacade.list(pageRequestDto.getPageRequest());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/board")
    public ResponseEntity<PageContentsDto<List<MyBoardListDto>>> boardList(@Valid PageRequestDto pageRequestDto) {
        PageContentsDto<List<MyBoardListDto>> result = myPageFacade.myBoardList(pageRequestDto.getPageRequest());
        return ResponseEntity.ok(result);
    }
}
