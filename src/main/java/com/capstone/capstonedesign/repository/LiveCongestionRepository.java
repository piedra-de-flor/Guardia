package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.LiveCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveCongestionRepository extends JpaRepository<LiveCongestion, Long> {
}
