package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CongestionBatchService {
    private final CongestionUpdater congestionUpdater;
    private final CCTVRepository cctvRepository;

    //@Scheduled(cron = "0 58 * * * *")
    @Transactional
    public void updateHourlyCongestion() {
        List<CCTV> cctvs =  cctvRepository.findAll();
        for (CCTV cctv : cctvs) {
            congestionUpdater.updateHourlyCongestion(cctv);
        }
    }

    //@Scheduled(cron = "0 58 23 * * *")
    @Transactional
    public void updateDayOfWeekCongestion() {
        List<CCTV> cctvs =  cctvRepository.findAll();

        for (CCTV cctv : cctvs) {
            congestionUpdater.updateDayOfWeekCongestion(cctv);
        }
    }

    //@Scheduled(cron = "0 58 23 L * *")
    @Transactional
    public void updateMonthlyCongestion() {
        List<CCTV> cctvs =  cctvRepository.findAll();

        for (CCTV cctv : cctvs) {
            congestionUpdater.updateMonthlyCongestion(cctv);
        }
    }
}
