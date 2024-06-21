package com.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}