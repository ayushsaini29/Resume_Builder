package com.dashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.entity.Template;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByEventTagsContaining(String tag);
}

