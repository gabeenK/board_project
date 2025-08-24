package com.ukm.ssgb.dto.myPage;

import com.ukm.ssgb.decorator.code.BusinessTypeNameCode;
import com.ukm.ssgb.decorator.code.FilePathCode;
import com.ukm.ssgb.decorator.code.NicknameGenerationRemainingCode;
import com.ukm.ssgb.decorator.code.RegionNameCode;
import com.ukm.ssgb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageProfileDto implements NicknameGenerationRemainingCode, FilePathCode, RegionNameCode, BusinessTypeNameCode {
    private Long userId;
    private String nickname;
    @Setter
    private int nicknameGenerationRemaining;
    @Setter
    private String filePath;
    private Long fileId;
    private Long profileImageId;
    private String email;
    @Setter
    private String regionName;
    private Long regionId;
    @Setter
    private String businessTypeName;
    private Long businessTypeId;

    public static MyPageProfileDto toDto(User user) {
        return MyPageProfileDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .fileId(user.getProfileImageId())
                .profileImageId(user.getProfileImageId())
                .email(user.getEmail())
                .regionId(user.getRegionId())
                .businessTypeId(user.getBusinessTypeId())
                .build();
    }
}
