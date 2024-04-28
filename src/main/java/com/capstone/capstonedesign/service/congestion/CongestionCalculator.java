package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.*;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import com.capstone.capstonedesign.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CongestionCalculator {
    private final AveragePopulationRepository populationRepository;
    private final CongestionRepository congestionRepository;
    private final LiveCongestionRepository liveCongestionRepository;
    private final HourlyCongestionRepository hourlyCongestionRepository;
    private final DayOfWeekCongestionRepository dayOfWeekCongestionRepository;
    private final MonthlyCongestionRepository monthlyCongestionRepository;

    private final static int C = 5;

    @Transactional
    public void calculateCongestion(int population) {
        Congestion congestion = new Congestion(population, calculateCongestionStatus(population));
        Congestion savedCongestion = congestionRepository.save(congestion);
        updateLiveCongestion(savedCongestion);
    }

    @Transactional
    public void calculateHourlyCongestion(int hour) {
        HourlyCongestion hourlyCongestion = hourlyCongestionRepository.getReferenceById(hour);
        LocalDateTime startDateTime = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(hour).withMinute(59).withSecond(59).withNano(999999999);

        List<Congestion> congestions = congestionRepository.findByDateTimeBetween(startDateTime, endDateTime);

        int population = 0;
        int amount = congestions.size();
        for (Congestion congestion : congestions) {
            population += congestion.getPopulation();
        }

        hourlyCongestion.updateAverage(population / amount);
        CongestionStatus status = calculateCongestionStatus(hourlyCongestion.getAverage());

        if (!hourlyCongestion.getStatus().equals(status.getStatus())) {
            hourlyCongestion.updateStatus(status);
        }
    }

    @Transactional
    public void calculateDayOfWeekCongestion(int population) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DayOfWeekCongestion dayOfWeekCongestion = dayOfWeekCongestionRepository.getReferenceById(dayOfWeek);
        dayOfWeekCongestion.updateAverage(population);
        CongestionStatus status = calculateCongestionStatus(dayOfWeekCongestion.getAverage());

        if (!dayOfWeekCongestion.getStatus().equals(status.getStatus())) {
            dayOfWeekCongestion.updateStatus(status);
        }
    }

    @Transactional
    public void calculateMonthlyCongestion(int population) {
        int month = LocalDate.now().getMonthValue();
        System.out.println(month);
        MonthlyCongestion monthlyCongestion = monthlyCongestionRepository.getReferenceById(month);
        monthlyCongestion.updateAverage(population);
        CongestionStatus status = calculateCongestionStatus(monthlyCongestion.getAverage());

        if (!monthlyCongestion.getStatus().equals(status.getStatus())) {
            monthlyCongestion.updateStatus(status);
        }
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

    private void updateLiveCongestion(Congestion congestion) {
        populationRepository.getReferenceById(1L).updateAverage(congestion.getPopulation());
        LiveCongestion liveCongestion = liveCongestionRepository.getReferenceById(1L);
        liveCongestion.updateStatus(congestion);
    }
}
