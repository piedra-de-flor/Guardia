package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.entity.congestion.LiveCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LiveCongestionRepository extends JpaRepository<LiveCongestion, CCTV> {
    Optional<LiveCongestion> findByCctv(CCTV cctv);
}
