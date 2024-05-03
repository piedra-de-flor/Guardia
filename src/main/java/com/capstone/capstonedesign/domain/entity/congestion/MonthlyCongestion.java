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
public class MonthlyCongestion {
    @Id
    private int month;
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

    public static List<MonthlyCongestion> createEmptyMonthlyCongestion(CCTV cctv) {
        List<MonthlyCongestion> response = new ArrayList<>();
        for (int month = 1; month < 13; month++) {
            MonthlyCongestion monthlyCongestion = new MonthlyCongestion(month, 0, 0, 0, null, cctv);
            response.add(monthlyCongestion);
        }
        return response;
    }
}
