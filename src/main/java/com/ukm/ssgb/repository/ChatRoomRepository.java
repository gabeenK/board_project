package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select c from ChatRoom c where c.chatRoomId in ?1 order by c.modifiedAt DESC")
    List<ChatRoom> findByChatRoomIdInModifiedAtDesc(Collection<Long> chatRoomIds);
}