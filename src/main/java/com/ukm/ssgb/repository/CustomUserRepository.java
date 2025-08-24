package com.ukm.ssgb.repository;

import com.ukm.ssgb.dto.usermanagement.GetUsersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserRepository {

    Page<GetUsersDto> findAllByQueryAndApproved(String query, Boolean approved, Pageable pageable);
}
