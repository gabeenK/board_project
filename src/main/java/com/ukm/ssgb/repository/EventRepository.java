package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

}
