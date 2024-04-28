package com.capstone.capstonedesign.domain.entity.congestion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
}
