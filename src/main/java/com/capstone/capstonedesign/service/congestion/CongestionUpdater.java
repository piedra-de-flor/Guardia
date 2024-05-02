package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.entity.congestion.DayOfWeekCongestion;
import com.capstone.capstonedesign.domain.entity.congestion.HourlyCongestion;
import com.capstone.capstonedesign.domain.entity.congestion.MonthlyCongestion;
import com.capstone.capstonedesign.domain.vo.CongestionStatus;
import com.capstone.capstonedesign.repository.AveragePopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CongestionUpdater {
    private final PopulationCalculator populationCalculator;
    private final CongestionCalculator congestionCalculator;

    @Async
    @Transactional
    public void updateHourlyCongestion(CCTV cctv) {
        int hour = LocalDateTime.now().getHour();
        HourlyCongestion hourlyCongestion = cctv.getHourlyCongestionList().get(hour);

        log.info("start update hourly congestion : {}", cctv.getId());

        int population = populationCalculator.calculateHourlyPopulation(hour, cctv.getId());
        hourlyCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(hourlyCongestion.getAverage());

        if (!hourlyCongestion.getStatus().equals(status.getStatus())) {
            hourlyCongestion.updateStatus(status);
        }

        log.info("update hourly congestion finish : {}", cctv.getId());
    }

    @Async
    @Transactional
    public void updateDayOfWeekCongestion(CCTV cctv) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DayOfWeekCongestion dayOfWeekCongestion = cctv.getDayOfWeekCongestions().get(dayOfWeek);

        log.info("start update day of week congestion : {}", cctv.getId());

        int population = populationCalculator.calculateDayOfWeekPopulation(cctv.getId());
        dayOfWeekCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(dayOfWeekCongestion.getAverage());

        if (!dayOfWeekCongestion.getStatus().equals(status.getStatus())) {
            dayOfWeekCongestion.updateStatus(status);
        }

        log.info("update day of week congestion finish : {}", cctv.getId());
    }

    @Async
    @Transactional
    public void updateMonthlyCongestion(CCTV cctv) {
        int month = LocalDate.now().getMonthValue();
        MonthlyCongestion monthlyCongestion = cctv.getMonthlyCongestions().get(month);

        log.info("start update monthly congestion : {}", cctv.getId());

        int endDay = LocalDate.now().getDayOfMonth();
        int population = populationCalculator.calculateMonthlyPopulation(endDay, cctv.getId());
        monthlyCongestion.updateAverage(population);
        CongestionStatus status = congestionCalculator.calculateCongestionStatus(monthlyCongestion.getAverage());

        if (!monthlyCongestion.getStatus().equals(status.getStatus())) {
            monthlyCongestion.updateStatus(status);
        }

        log.info("update monthly congestion finish : {}", cctv.getId());
    }
}
