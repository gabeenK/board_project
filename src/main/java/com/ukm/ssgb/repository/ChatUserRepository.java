package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    @Query("select c from ChatUser c where c.userId = ?1 order by c.createdAt DESC")
    List<ChatUser> findByUserIdCreatedAtDesc(Long userId);

    Optional<ChatUser> findByChatRoomIdAndUserIdNot(Long chatRoomId, Long userId);
}