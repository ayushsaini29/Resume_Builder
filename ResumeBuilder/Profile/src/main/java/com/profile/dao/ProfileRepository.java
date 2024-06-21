package com.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
