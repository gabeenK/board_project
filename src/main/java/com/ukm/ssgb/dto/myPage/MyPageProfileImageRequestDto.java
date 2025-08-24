package com.ukm.ssgb.dto.myPage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class MyPageProfileImageRequestDto {

    @NotNull
    MultipartFile profileImage;
}
