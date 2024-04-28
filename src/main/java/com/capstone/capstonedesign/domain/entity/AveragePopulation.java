package com.capstone.capstonedesign.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AveragePopulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double average;
    private int population;
    private int amount;

    public void updateAverage(int population) {
        this.amount++;
        this.population += population;
        this.average = (double) this.population / amount;
    }
}
