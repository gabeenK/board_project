package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.NicknameDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.Nickname;
import com.ukm.ssgb.model.NicknameCount;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.NicknameCountRepository;
import com.ukm.ssgb.repository.NicknameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class NicknameService {

    private final NicknameRepository nicknameRepository;
    private final NicknameCountRepository nicknameCountRepository;
    private final Queue<NicknameDto> nicknameQueue = new ConcurrentLinkedQueue<>();

    @Transactional
    public void deleteByUserIdAndInsertAsInactive(long userId) {
        // 유저가 사용중인 닉네임을 찾아서 삭제한 후, 동일한 데이터로 재 생성
        nicknameRepository.findByUserId(userId).ifPresent(nickname -> {

            Nickname newNickname = Nickname.builder()
                    .noun(nickname.getNoun())
                    .adjective(nickname.getAdjective())
                    .build();

            nicknameRepository.delete(nickname);
            nicknameRepository.save(newNickname);
        });
    }

    @Transactional
    public void updateUserNickname(User user) {
        plusNicknameCount(user.getUserId());

        NicknameDto nicknameDto = getRandomNickname();
        Nickname nickname = nicknameRepository.findByNicknameIdAndActiveFalse(nicknameDto.getNicknameId()).orElseThrow();
        nickname.activeNickname(user.getUserId());

        user.setNickname(nickname.getNickName());
    }

    private void plusNicknameCount(long userId) {
        NicknameCount nicknameCount = nicknameCountRepository.findByUserId(userId)
                .orElseGet(() -> nicknameCountRepository.save(new NicknameCount(userId)));

        if (nicknameCount.overMaxCount()) {
            throw new ValidationException("이번달 랜덤 변경 가능한 횟수를 초과했습니다. 다음달 1일에 초기화되어 변경가능합니다.");
        }

        nicknameCount.plusCount();
    }

    public NicknameDto getRandomNickname() {
        NicknameDto nicknameDto = nicknameQueue.poll();
        if (nonNull(nicknameDto)) {
            return nicknameDto;
        }

        fetchNicknames();
        return nicknameQueue.poll();
    }

    private synchronized void fetchNicknames() {
        if (!nicknameQueue.isEmpty()) {
            return;
        }

        nicknameQueue.addAll(nicknameRepository.findRandomByActiveFalseLimit100()
                .stream()
                .map(NicknameDto::of)
                .toList());
    }

    @Transactional
    public void active(Long userId, Long nicknameId) {
        Nickname nickname = nicknameRepository.findByNicknameIdAndActiveFalse(nicknameId).orElseThrow();
        nickname.activeNickname(userId);
    }

    @Transactional
    public void deleteNicknameCount() {
        nicknameCountRepository.deleteAllInBatch();
    }
}
