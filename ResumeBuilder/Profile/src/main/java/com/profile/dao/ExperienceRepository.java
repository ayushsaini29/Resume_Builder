package com.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
