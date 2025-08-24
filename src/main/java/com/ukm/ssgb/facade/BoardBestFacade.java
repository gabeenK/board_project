package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.board.BestConfigDto;
import com.ukm.ssgb.dto.board.BestConfigsDto;
import com.ukm.ssgb.dto.board.BestListDto;
import com.ukm.ssgb.dto.board.BoardBestListDto;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.page.PageContentsWithBestSizeDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.model.BestConfig;
import com.ukm.ssgb.repository.BestConfigRepository;
import com.ukm.ssgb.service.BoardBestService;
import com.ukm.ssgb.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardBestFacade {

    private final BoardBestService boardBestService;
    private final BestConfigRepository bestConfigRepository;

    @Transactional(readOnly = true)
    public PageContentsDto<List<BoardBestListDto>> listByUser() {
        List<BoardBestListDto> bestList = boardBestService.bestListByUser();

        return PageContentsDto.of(bestList, (long) bestList.size());
    }

    @Transactional(readOnly = true)
    public PageContentsWithBestSizeDto<List<BestListDto>> list(PageRequestDto pageRequestDto) {
        Integer bestSize = boardBestService.bestSize();
        List<BestListDto> bestList = boardBestService.bestList();
        List<BestListDto> bestListWithPaging = PageUtil.paging(bestList, pageRequestDto);

        return PageContentsWithBestSizeDto.of(bestListWithPaging, (long) bestList.size(), bestSize);
    }

    @Transactional(readOnly = true)
    public BestConfigsDto configList() {
        List<BestConfigDto> bestConfigs = bestConfigRepository.findAll()
                .stream()
                .map(BestConfigDto::toDto)
                .toList();

        return new BestConfigsDto(bestConfigs);
    }

    @Transactional
    public void updateConfig(int bestSize) {
        bestConfigRepository.updateAllConfigsInactive();

        BestConfig bestConfig = bestConfigRepository.findByBestSize(bestSize).orElseThrow();
        bestConfig.active();
    }
}
