package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findFirstByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);

    @Query("select c from Chat c where c.chatRoomId = ?1 order by c.createdAt DESC")
    Page<Chat> findByChatRoomIdCreatedAtDesc(Long chatRoomId, Pageable pageable);
}