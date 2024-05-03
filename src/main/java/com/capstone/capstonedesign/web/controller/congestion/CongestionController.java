package com.capstone.capstonedesign.web.controller.congestion;

import com.capstone.capstonedesign.dto.cctv.CCTVDto;
import com.capstone.capstonedesign.dto.congestion.LiveCongestionDto;
import com.capstone.capstonedesign.dto.congestion.PeriodCongestionDto;
import com.capstone.capstonedesign.service.congestion.CongestionBatchService;
import com.capstone.capstonedesign.service.congestion.CongestionService;
import com.capstone.capstonedesign.service.congestion.LiveCongestionBatchService;
import com.capstone.capstonedesign.service.congestion.LiveCongestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CongestionController {
    private final CongestionService service;
    private final CongestionBatchService batchService;
    private final LiveCongestionService liveCongestionBatchService;

    @GetMapping("/live-congestion")
    public ResponseEntity<LiveCongestionDto> readLiveCongestion(@RequestBody CCTVDto dto) {
        LiveCongestionDto response = service.readLiveCongestion(dto.cctvId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hourly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readHourlyCongestion(@RequestBody CCTVDto dto) {
        List<PeriodCongestionDto> response = service.readHourlyCongestion(dto.cctvId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/day-of-week-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readDayOfWeekCongestion(@RequestBody CCTVDto dto) {
        List<PeriodCongestionDto> response = service.readDayOfWeekCongestion(dto.cctvId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readMonthlyCongestion(@RequestBody CCTVDto dto) {
        List<PeriodCongestionDto> response = service.readMonthlyCongestion(dto.cctvId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-hour")
    public void testHour() {
        batchService.updateHourlyCongestion();
    }

    @GetMapping("/test-day")
    public void testDay() {
        batchService.updateDayOfWeekCongestion();
    }

    @GetMapping("/test-month")
    public void testMonth() {
        batchService.updateMonthlyCongestion();
    }

    @GetMapping("/test-live/{pop}/{cctv}")
    public void testLive(@PathVariable int pop, @PathVariable long cctv) {
        liveCongestionBatchService.calculateCongestion(pop, cctv);
    }

    @GetMapping("/test-live2/{pop}/{cctv}")
    public void testLive2(@PathVariable int pop, @PathVariable long cctv) {
        liveCongestionBatchService.testLive(pop, cctv);
    }
}
