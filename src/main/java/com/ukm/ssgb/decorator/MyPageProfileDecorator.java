package com.ukm.ssgb.decorator;

import com.ukm.ssgb.decorator.filler.BusinessTypeNameFiller;
import com.ukm.ssgb.decorator.filler.FilePathFiller;
import com.ukm.ssgb.decorator.filler.NicknameGenerationRemainingFiller;
import com.ukm.ssgb.decorator.filler.RegionNameFiller;
import com.ukm.ssgb.dto.myPage.MyPageProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPageProfileDecorator {
    private final NicknameGenerationRemainingFiller nicknameGenerationRemainingFiller;
    private final FilePathFiller filePathFiller;
    private final RegionNameFiller regionNameFiller;
    private final BusinessTypeNameFiller businessTypeNameFiller;

    public void decorate(MyPageProfileDto dto) {
        if (dto == null) {
            return;
        }
        nicknameGenerationRemainingFiller.fill(dto);
        filePathFiller.fill(dto);
        regionNameFiller.fill(dto);
        businessTypeNameFiller.fill(dto);
    }
}