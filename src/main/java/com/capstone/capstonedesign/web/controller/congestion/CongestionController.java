package com.capstone.capstonedesign.web.controller.congestion;

import com.capstone.capstonedesign.dto.congestion.LiveCongestionDto;
import com.capstone.capstonedesign.dto.congestion.PeriodCongestionDto;
import com.capstone.capstonedesign.service.congestion.CongestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CongestionController {
    private final CongestionService service;

    @GetMapping("/live-congestion")
    public ResponseEntity<LiveCongestionDto> readLiveCongestion() {
        LiveCongestionDto response = service.readLiveCongestion();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hourly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readHourlyCongestion() {
        List<PeriodCongestionDto> response = service.readHourlyCongestion();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/day-of-week-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readDayOfWeekCongestion() {
        List<PeriodCongestionDto> response = service.readDayOfWeekCongestion();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readMonthlyCongestion() {
        List<PeriodCongestionDto> response = service.readMonthlyCongestion();
        return ResponseEntity.ok(response);
    }
}
