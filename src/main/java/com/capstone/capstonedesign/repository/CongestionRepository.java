package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.congestion.Congestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CongestionRepository extends JpaRepository<Congestion, LocalDateTime> {
    List<Congestion> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}