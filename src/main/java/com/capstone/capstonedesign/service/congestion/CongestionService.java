package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.*;
import com.capstone.capstonedesign.dto.congestion.LiveCongestionDto;
import com.capstone.capstonedesign.dto.congestion.PeriodCongestionDto;
import com.capstone.capstonedesign.repository.DayOfWeekCongestionRepository;
import com.capstone.capstonedesign.repository.HourlyCongestionRepository;
import com.capstone.capstonedesign.repository.LiveCongestionRepository;
import com.capstone.capstonedesign.repository.MonthlyCongestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CongestionService {
    private final LiveCongestionRepository liveCongestionRepository;
    private final HourlyCongestionRepository hourlyCongestionRepository;
    private final DayOfWeekCongestionRepository dayOfWeekCongestionRepository;
    private final MonthlyCongestionRepository monthlyCongestionRepository;

    public LiveCongestionDto readLiveCongestion() {
        LiveCongestion liveCongestion = liveCongestionRepository.getReferenceById(0L);
        Congestion congestion = liveCongestion.getCongestion();

        return new LiveCongestionDto(
                congestion.getDateTime().toString(),
                congestion.getStatus());
    }

    public List<PeriodCongestionDto> readHourlyCongestion() {
        List<HourlyCongestion> hourlyCongestions = hourlyCongestionRepository.findAll();
        List<PeriodCongestionDto> response = new ArrayList<>();

        for (HourlyCongestion hourlyCongestion : hourlyCongestions) {
            response.add(new PeriodCongestionDto(
                    hourlyCongestion.getHourOfDay(),
                    hourlyCongestion.getAverage(),
                    hourlyCongestion.getStatus()
            ));
        }

        return response;
    }

    public List<PeriodCongestionDto> readDayOfWeekCongestion() {
        List<DayOfWeekCongestion> dayOfWeekCongestions = dayOfWeekCongestionRepository.findAll();
        List<PeriodCongestionDto> response = new ArrayList<>();

        for (DayOfWeekCongestion dayOfWeekCongestion : dayOfWeekCongestions) {
            response.add(new PeriodCongestionDto(
                    dayOfWeekCongestion.getDayOfWeek(),
                    dayOfWeekCongestion.getAverage(),
                    dayOfWeekCongestion.getStatus()
            ));
        }

        return response;
    }

    public List<PeriodCongestionDto> readMonthlyCongestion() {
        List<MonthlyCongestion> monthlyCongestions = monthlyCongestionRepository.findAll();
        List<PeriodCongestionDto> response = new ArrayList<>();

        for (MonthlyCongestion monthlyCongestion : monthlyCongestions) {
            response.add(new PeriodCongestionDto(
                    monthlyCongestion.getMonth(),
                    monthlyCongestion.getAverage(),
                    monthlyCongestion.getStatus()
            ));
        }

        return response;
    }
}
