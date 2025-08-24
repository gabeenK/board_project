package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByEmailAndDeletedFalse(String email);

    @Query("select u from User u where u.userId = ?1 and u.approved = false and u.deleted = false")
    List<User> findAllByIdAndApprovedFalseAndDeletedFalse(Collection<Long> userIds);
}