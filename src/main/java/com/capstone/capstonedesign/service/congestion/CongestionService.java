package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.entity.congestion.*;
import com.capstone.capstonedesign.dto.congestion.LiveCongestionDto;
import com.capstone.capstonedesign.dto.congestion.PeriodCongestionDto;
import com.capstone.capstonedesign.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CongestionService {
    private final CCTVRepository cctvRepository;

    public LiveCongestionDto readLiveCongestion(long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        LiveCongestion liveCongestion = cctv.getLiveCongestion();
        Congestion congestion = liveCongestion.getCongestion();

        return new LiveCongestionDto(
                congestion.getDateTime().toString(),
                congestion.getStatus());
    }

    public List<PeriodCongestionDto> readHourlyCongestion(long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        List<HourlyCongestion> hourlyCongestions = cctv.getHourlyCongestions();
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

    public List<PeriodCongestionDto> readDayOfWeekCongestion(long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        List<DayOfWeekCongestion> dayOfWeekCongestions = cctv.getDayOfWeekCongestions();
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

    public List<PeriodCongestionDto> readMonthlyCongestion(long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        List<MonthlyCongestion> monthlyCongestions = cctv.getMonthlyCongestions();
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
