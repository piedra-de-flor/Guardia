package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.HourlyCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyCongestionRepository extends JpaRepository<HourlyCongestion, Integer> {
}
