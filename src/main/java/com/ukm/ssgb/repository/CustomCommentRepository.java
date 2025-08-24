package com.ukm.ssgb.repository;

import com.ukm.ssgb.dto.comment.AdminCommentListDto;
import com.ukm.ssgb.dto.comment.AdminCommentsRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomCommentRepository {

    Page<AdminCommentListDto> findAllByAdmin(AdminCommentsRequestDto requestDto);

    List<AdminCommentListDto> findAllChildCommentByAdmin(List<Long> parentCommentIds);
}
