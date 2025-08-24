package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.UserMac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMacRepository extends JpaRepository<UserMac, Long> {
    Optional<UserMac> findByMacAddress(String macAddress);
}