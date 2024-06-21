package com.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
