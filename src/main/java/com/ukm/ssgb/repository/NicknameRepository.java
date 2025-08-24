package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {
    Optional<Nickname> findByUserId(Long userId);

    @Query("select n from Nickname n where n.active = false ORDER BY RAND() LIMIT 100")
    List<Nickname> findRandomByActiveFalseLimit100();

    Optional<Nickname> findByNicknameIdAndActiveFalse(Long nicknameId);
}