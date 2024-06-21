package com.dashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}

