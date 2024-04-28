package com.capstone.capstonedesign.domain.entity;

import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Congestion {
    @Id
    private LocalDateTime dateTime;
    private String status;
    private int population;

    public Congestion(int population, CongestionStatus status) {
        this.population = population;
        this.status = status.getStatus();
        this.dateTime = LocalDateTime.now();
    }
}
