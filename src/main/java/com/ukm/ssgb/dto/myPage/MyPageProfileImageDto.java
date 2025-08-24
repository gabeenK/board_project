package com.ukm.ssgb.dto.myPage;

import com.ukm.ssgb.decorator.code.FilePathCode;
import com.ukm.ssgb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageProfileImageDto implements FilePathCode {
    @Setter
    private String filePath;
    private Long fileId;
    private Long profileImageId;

    public static MyPageProfileImageDto toDto(User user) {
        return MyPageProfileImageDto.builder()
                .fileId(user.getProfileImageId())
                .profileImageId(user.getProfileImageId())
                .build();
    }
}
