package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DayOfWeekCongestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int dayOfWeek;
    private double average;
    private int population;
    private int amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "cctv_id")
    private CCTV cctv;

    @Builder
    public DayOfWeekCongestion(int dayOfWeek, double average, int population, int amount, String status, CCTV cctv) {
        this.dayOfWeek = dayOfWeek;
        this.average = average;
        this.population = population;
        this.amount = amount;
        this.status = status;
        this.cctv = cctv;
    }

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
            DayOfWeekCongestion dayOfWeekCongestion = DayOfWeekCongestion.builder()
                    .dayOfWeek(dayOfWeek)
                    .average(0)
                    .population(0)
                    .amount(0)
                    .status(null)
                    .cctv(cctv)
                    .build();

            response.add(dayOfWeekCongestion);
        }
        return response;
    }
}
