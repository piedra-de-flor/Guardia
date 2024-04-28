package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.service.ai.PythonRunner;
import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import com.capstone.capstonedesign.service.support.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

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

    public void test(int population) {
        congestionCalculator.test(population);
    }

    public void test1(int population) {
        congestionCalculator.calculateCongestion(population);
    }

    public void test2(int population) {
        congestionCalculator.calculateHourlyCongestion(population);
    }
}
