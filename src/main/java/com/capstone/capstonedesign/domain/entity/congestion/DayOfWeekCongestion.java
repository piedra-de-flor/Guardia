package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DayOfWeekCongestion {
    @Id
    private int dayOfWeek;
    private double average;
    private int population;
    private int amount;
    private String status;

    public void updateAverage(int population) {
        this.amount++;
        this.population += population;
        this.average = (double) this.population / amount;
    }

    public void updateStatus(CongestionStatus status) {
        this.status = status.getStatus();
    }
}
