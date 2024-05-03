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
public class HourlyCongestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int hourOfDay;
    private double average;
    private int population;
    private int amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "cctv_id")
    private CCTV cctv;

    @Builder
    public HourlyCongestion(int time, double average, int population, int amount, CCTV cctv) {
        this.hourOfDay = time;
        this.average = average;
        this.population = population;
        this.amount = amount;
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

    public static List<HourlyCongestion> createEmptyHourlyCongestion(CCTV cctv) {
        List<HourlyCongestion> response = new ArrayList<>();
        for (int time = 0; time < 24; time++) {
            HourlyCongestion hourlyCongestion = HourlyCongestion.builder()
                    .time(time)
                    .average(0)
                    .amount(0)
                    .population(0)
                    .cctv(cctv)
                    .build();

            response.add(hourlyCongestion);
        }
        return response;
    }
}
