package com.ukm.ssgb.repository;

import com.ukm.ssgb.dto.board.AdminBoardListDto;
import com.ukm.ssgb.dto.board.AdminBoardsRequestDto;
import org.springframework.data.domain.Page;

public interface CustomBoardRepository {

    Page<AdminBoardListDto> findAllByAdmin(AdminBoardsRequestDto requestDto);
}
