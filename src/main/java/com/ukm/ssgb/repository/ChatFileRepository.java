package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.ChatFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFileRepository extends JpaRepository<ChatFile, Long> {
}