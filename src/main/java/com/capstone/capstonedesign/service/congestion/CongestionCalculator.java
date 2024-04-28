package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.Congestion;
import com.capstone.capstonedesign.domain.entity.congestion.HourlyCongestion;
import com.capstone.capstonedesign.domain.entity.congestion.LiveCongestion;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import com.capstone.capstonedesign.repository.AveragePopulationRepository;
import com.capstone.capstonedesign.repository.CongestionRepository;
import com.capstone.capstonedesign.repository.HourlyCongestionRepository;
import com.capstone.capstonedesign.repository.LiveCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CongestionCalculator {
    private final AveragePopulationRepository populationRepository;
    private final CongestionRepository congestionRepository;
    private final LiveCongestionRepository liveCongestionRepository;
    private final HourlyCongestionRepository hourlyCongestionRepository;
    private final static int C = 5;

    @Transactional
    public void calculateCongestion(int population) {
        Congestion congestion = new Congestion(population, calculateCongestionStatus(population));
        Congestion savedCongestion = congestionRepository.save(congestion);
        populationRepository.getReferenceById(1L).updateAverage(population);
        LiveCongestion liveCongestion = liveCongestionRepository.getReferenceById(1L);
        liveCongestion.updateStatus(savedCongestion);
    }

    @Transactional
    public void calculateHourlyCongestion(int population) {
        int hour = LocalDateTime.now().getHour();
        HourlyCongestion hourlyCongestion = hourlyCongestionRepository.getReferenceById(hour);
        hourlyCongestion.updateAverage(population);
        CongestionStatus status = calculateCongestionStatus(hourlyCongestion.getAverage());

        if (!hourlyCongestion.getStatus().equals(status.getStatus())) {
            hourlyCongestion.updateStatus(status);
        }
    }

    @Transactional
    public void calculateDayOfWeekCongestion(int population) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        System.out.println(dayOfWeek);

    }

    public void test(int population) {
        Congestion congestion = new Congestion(population, calculateCongestionStatus(population));
        congestionRepository.save(congestion);
        LiveCongestion liveCongestion = new LiveCongestion(congestion);
        liveCongestionRepository.save(liveCongestion);
    }

    private CongestionStatus calculateCongestionStatus(double population) {
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
