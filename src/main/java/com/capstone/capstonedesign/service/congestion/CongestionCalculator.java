package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import com.capstone.capstonedesign.repository.AveragePopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CongestionCalculator {
    private final AveragePopulationRepository populationRepository;
    private final static int C = 5;

    public CongestionStatus calculateCongestionStatus(double population) {
        double average = populationRepository.getReferenceById(1L).getAverage();

        if (average - C <= population && average + C >= population) {
            return CongestionStatus.COMMON;
        } else if (average - C > population) {
            return CongestionStatus.NOT_MANY_PEOPLE;
        } else {
            return CongestionStatus.CONFUSION;
        }
    }
}
