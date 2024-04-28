package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.Congestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CongestionRepository extends JpaRepository<Congestion, LocalDateTime> {
}
