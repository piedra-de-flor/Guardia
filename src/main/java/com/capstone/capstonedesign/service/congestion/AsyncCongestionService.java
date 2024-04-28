package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.DayOfWeekCongestion;
import com.capstone.capstonedesign.domain.entity.congestion.HourlyCongestion;
import com.capstone.capstonedesign.domain.entity.congestion.MonthlyCongestion;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import com.capstone.capstonedesign.repository.DayOfWeekCongestionRepository;
import com.capstone.capstonedesign.repository.HourlyCongestionRepository;
import com.capstone.capstonedesign.repository.MonthlyCongestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncCongestionService {
    private final HourlyCongestionRepository hourlyCongestionRepository;
    private final DayOfWeekCongestionRepository dayOfWeekCongestionRepository;
    private final MonthlyCongestionRepository monthlyCongestionRepository;
    private final PopulationCalculator populationCalculator;
    private final CongestionCalculator congestionCalculator;

    @Async
    @Scheduled(cron = "0 59 * * * *")
    @Transactional
    public void updateHourlyCongestion() {
        int hour = LocalDateTime.now().getHour();
        HourlyCongestion hourlyCongestion = hourlyCongestionRepository.getReferenceById(hour);
        int population = populationCalculator.calculateHourlyPopulation(hour);
        hourlyCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(hourlyCongestion.getAverage());

        if (!hourlyCongestion.getStatus().equals(status.getStatus())) {
            hourlyCongestion.updateStatus(status);
        }
    }

    @Async
    @Scheduled(cron = "0 58 23 * * *")
    @Transactional
    public void updateDayOfWeekCongestion() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DayOfWeekCongestion dayOfWeekCongestion = dayOfWeekCongestionRepository.getReferenceById(dayOfWeek);
        int population = populationCalculator.calculateDayOfWeekPopulation();
        dayOfWeekCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(dayOfWeekCongestion.getAverage());

        if (!dayOfWeekCongestion.getStatus().equals(status.getStatus())) {
            dayOfWeekCongestion.updateStatus(status);
        }
    }

    @Async
    @Scheduled(cron = "0 59 23 L * ?")
    @Transactional
    public void updateMonthlyCongestion() {
        int month = LocalDate.now().getMonthValue();
        MonthlyCongestion monthlyCongestion = monthlyCongestionRepository.getReferenceById(month);
        int endDay = LocalDate.now().getDayOfMonth();
        int population = populationCalculator.calculateMonthlyPopulation(endDay);
        monthlyCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(monthlyCongestion.getAverage());

        if (!monthlyCongestion.getStatus().equals(status.getStatus())) {
            monthlyCongestion.updateStatus(status);
        }
    }
}
