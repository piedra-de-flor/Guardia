package com.capstone.capstonedesign.domain.entity.congestion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class MonthlyCongestion {
    @Id
    private LocalDate date;
}
