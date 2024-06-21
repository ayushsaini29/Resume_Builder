package com.dashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
}
