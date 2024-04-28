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

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncLiveCongestionService {
    private final LiveCongestionService liveCongestionService;
    private final FrameGrabber frameGrabber;
    private final PythonRunner pythonRunner;
    private final FileManager fileManager;

    @Async
    @Scheduled(cron = "0 0/5 * * * *")
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
}
