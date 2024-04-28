package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.congestion.MonthlyCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyCongestionRepository extends JpaRepository<MonthlyCongestion, Integer> {
}
