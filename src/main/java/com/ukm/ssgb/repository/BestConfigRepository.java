package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.BestConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BestConfigRepository extends JpaRepository<BestConfig, Long> {
    Optional<BestConfig> findByActiveIsTrue();

    Optional<BestConfig> findByBestSize(Integer bestSize);

    @Modifying
    @Query("update BestConfig b set b.active = false")
    void updateAllConfigsInactive();
}
