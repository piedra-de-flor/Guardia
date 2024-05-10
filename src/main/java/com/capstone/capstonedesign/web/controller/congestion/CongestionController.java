package com.capstone.capstonedesign.web.controller.congestion;

import com.capstone.capstonedesign.dto.cctv.CCTVDto;
import com.capstone.capstonedesign.dto.congestion.LiveCongestionDto;
import com.capstone.capstonedesign.dto.congestion.PeriodCongestionDto;
import com.capstone.capstonedesign.service.congestion.CongestionBatchService;
import com.capstone.capstonedesign.service.congestion.CongestionService;
import com.capstone.capstonedesign.service.congestion.LiveCongestionBatchService;
import com.capstone.capstonedesign.service.congestion.LiveCongestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CongestionController {
    private final CongestionService service;
    private final CongestionBatchService batchService;
    private final LiveCongestionService liveCongestionBatchService;

    @Operation(summary = "실시간 혼잡도 조회", description = "요청으로 받은 cctv의 실시간 혼잡도를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/live-congestion")
    public ResponseEntity<LiveCongestionDto> readLiveCongestion(@RequestParam long cctvId) {
        LiveCongestionDto response = service.readLiveCongestion(cctvId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시간별 혼잡도 조회", description = "요청으로 받은 cctv의 시간별 혼잡도를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/hourly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readHourlyCongestion(@RequestParam long cctvId) {
        List<PeriodCongestionDto> response = service.readHourlyCongestion(cctvId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "요일별 혼잡도 조회", description = "요청으로 받은 cctv의 요일별 혼잡도를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/day-of-week-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readDayOfWeekCongestion(@RequestParam long cctvId) {
        List<PeriodCongestionDto> response = service.readDayOfWeekCongestion(cctvId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "월별 혼잡도 조회", description = "요청으로 받은 cctv의 월별 혼잡도를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "403", description = "JWT토큰이 잘못되었습니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/monthly-congestion")
    public ResponseEntity<List<PeriodCongestionDto>> readMonthlyCongestion(@RequestParam long cctvId) {
        List<PeriodCongestionDto> response = service.readMonthlyCongestion(cctvId);
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
