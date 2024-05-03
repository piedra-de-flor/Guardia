package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "cctv_id")
    private CCTV cctv;

    public void updateAverage(int population) {
        this.amount++;
        this.population += population;
        this.average = (double) this.population / amount;
    }

    public void updateStatus(CongestionStatus status) {
        this.status = status.getStatus();
    }

    public static List<DayOfWeekCongestion> createEmptyDayOfWeekCongestion(CCTV cctv) {
        List<DayOfWeekCongestion> response = new ArrayList<>();
        for (int dayOfWeek = 1; dayOfWeek < 8; dayOfWeek++) {
            DayOfWeekCongestion dayOfWeekCongestion = new DayOfWeekCongestion(dayOfWeek, 0, 0, 0, null, cctv);
            response.add(dayOfWeekCongestion);
        }
        return response;
    }
}
