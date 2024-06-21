package com.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
