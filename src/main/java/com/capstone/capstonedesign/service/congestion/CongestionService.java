package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.service.ai.PythonRunner;
import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import com.capstone.capstonedesign.service.support.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CongestionService {
    private final FrameGrabber frameGrabber;
    private final PythonRunner pythonRunner;
    private final FileManager fileManager;
    private final CongestionCalculator congestionCalculator;

    @Transactional
    public void updateLiveCongestion() {
        String filePath = null;

        try {
            filePath = frameGrabber.frameGrab();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        pythonRunner.runPythonScript(filePath);
    }

    @Async
    @Scheduled(cron = "0 59 * * * *")
    public void updateHourlyCongestion() {
        int hour = LocalDateTime.now().getHour();
        congestionCalculator.calculateHourlyCongestion(hour);
    }
}
