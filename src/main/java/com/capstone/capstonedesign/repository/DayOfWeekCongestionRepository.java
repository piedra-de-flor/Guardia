package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.congestion.DayOfWeekCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOfWeekCongestionRepository extends JpaRepository<DayOfWeekCongestion, Integer> {
}
