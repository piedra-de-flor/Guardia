package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.Congestion;
import com.capstone.capstonedesign.repository.CongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PopulationCalculator {
    private final CongestionRepository congestionRepository;

    public int calculateHourlyPopulation(int hour, long cctvId) {
        LocalDateTime startDateTime = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(hour).withMinute(59).withSecond(59).withNano(999999999);

        List<Congestion> congestions = congestionRepository.findByDateTimeBetween(startDateTime, endDateTime).stream()
                .filter(congestion -> congestion.getCctv().getId() == cctvId)
                .toList();

        int population = 0;
        int amount = congestions.size();
        for (Congestion congestion : congestions) {
            population += congestion.getPopulation();
        }

        return population / amount;
    }

    public int calculateDayOfWeekPopulation(long cctvId) {
        int population = 0;
        for (int time = 0; time < 24; time++) {
            population += calculateHourlyPopulation(time, cctvId);
        }

        return population;
    }

    public int calculateMonthlyPopulation(int endDay, long cctvId) {
        int population = 0;
        for (int day = 1; day <= endDay; day++) {
            population += calculateDayOfWeekPopulation(cctvId);
        }

        return population;
    }
}
