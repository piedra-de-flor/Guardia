package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Congestion {
    @Id
    private LocalDateTime dateTime;
    private String status;
    private int population;

    @ManyToOne
    @JoinColumn(name = "cctv_id")
    private CCTV cctv;

    public Congestion(int population, CongestionStatus status) {
        this.population = population;
        this.status = status.getStatus();
        this.dateTime = LocalDateTime.now();
    }
}
