package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.board.BestListDto;
import com.ukm.ssgb.dto.board.BoardBestListDto;
import com.ukm.ssgb.model.BestConfig;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.repository.BestConfigRepository;
import com.ukm.ssgb.repository.BoardRepository;
import com.ukm.ssgb.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BoardBestService {

    private final BestConfigRepository bestConfigRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Integer bestSize() {
        BestConfig bestConfig = bestConfigRepository.findByActiveIsTrue().orElseThrow();
        return bestConfig.getBestSize();
    }

    @Transactional(readOnly = true)
    public List<BestListDto> bestList() {
        // 이번달 게시물 || 고정 게시물
        List<Board> boards = boardRepository.findByCreatedAtBetweenOrPinIsTrue(DateTimeUtil.startDateOfLastMonth(), DateTimeUtil.startDateOfTomorrow());

        // 인기 점수로 정렬
        List<BestListDto> bestList = boards.stream()
                .map(BestListDto::toDto)
                .sorted(Comparator.comparingDouble(BestListDto::getScore).reversed())
                .toList();

        // 순위 추가
        AtomicInteger rank = new AtomicInteger();
        bestList.forEach(dto -> dto.setRank(dto.isPin() ? 0 : rank.incrementAndGet()));

        return bestList;
    }

    @Transactional(readOnly = true)
    public List<BoardBestListDto> bestListByUser() {
        // 이번달 게시물 || 고정 게시물
        List<Board> boards = boardRepository.findByCreatedAtBetweenOrPinIsTrue(DateTimeUtil.startDateOfLastMonth(), DateTimeUtil.startDateOfTomorrow());

        // 인기 점수로 정렬
        List<BoardBestListDto> bestList = boards.stream()
                .map(BoardBestListDto::toDto)
                .sorted(Comparator.comparingDouble(BoardBestListDto::getScore).reversed())
                .toList();

        List<BoardBestListDto> pinnedList = bestList.stream()
                .filter(BoardBestListDto::isPin)
                .toList();

        List<BoardBestListDto> nonPinnedBestList = bestList.stream()
                .filter(dto -> !dto.isPin())
                .limit(bestSize())
                .toList();

        List<BoardBestListDto> result = new ArrayList<>();
        result.addAll(pinnedList);
        result.addAll(nonPinnedBestList);

        return result;
    }
}
