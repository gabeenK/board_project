package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.NicknameCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NicknameCountRepository extends JpaRepository<NicknameCount, Long> {

    Optional<NicknameCount> findByUserId(Long userId);
}
