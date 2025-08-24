package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.NicknameGenerationRemainingCode;
import com.ukm.ssgb.model.NicknameCount;
import com.ukm.ssgb.repository.NicknameCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameGenerationRemainingFiller {

    private final static int defaultRemainingCount = 5;

    private final NicknameCountRepository nicknameCountRepository;

    public void fill(NicknameGenerationRemainingCode dto) {
        int count = nicknameCountRepository.findByUserId(dto.getUserId())
                .map(NicknameCount::getRemainingCount)
                .orElse(defaultRemainingCount);

        dto.setNicknameGenerationRemaining(count);
    }
}