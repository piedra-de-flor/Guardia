package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MonthlyCongestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int month;
    private double average;
    private int population;
    private int amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "cctv_id")
    private CCTV cctv;

    @Builder
    public MonthlyCongestion(int month, double average, int population, int amount, String status, CCTV cctv) {
        this.month = month;
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

    public static List<MonthlyCongestion> createEmptyMonthlyCongestion(CCTV cctv) {
        List<MonthlyCongestion> response = new ArrayList<>();
        for (int month = 1; month < 13; month++) {
            MonthlyCongestion monthlyCongestion = MonthlyCongestion.builder()
                    .month(month)
                    .average(0)
                    .population(0)
                    .amount(0)
                    .status(null)
                    .cctv(cctv)
                    .build();
            response.add(monthlyCongestion);
        }
        return response;
    }
}
