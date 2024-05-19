package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.repository.CCTVRepository;
import com.capstone.capstonedesign.service.ai.PythonRunner;
import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import com.capstone.capstonedesign.service.support.FileManager;
import com.capstone.capstonedesign.service.webClinet.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LiveCongestionBatchService {
    private final LiveCongestionService liveCongestionService;
    private final FrameGrabber frameGrabber;
    private final WebClientService webClientService;
    private final CCTVRepository cctvRepository;
    private final PythonRunner pythonRunner;
    private final FileManager fileManager;

    @Async
    @Scheduled(cron = "0 0/5 * * * *")
    @Transactional
    public void updateLiveCongestion() {
        List<CCTV> cctvs = cctvRepository.findAll();

        for (CCTV cctv : cctvs) {
            byte[] imageBytes;

            try {
                imageBytes = frameGrabber.frameGrab();
            } catch (IOException e) {
                throw new IllegalArgumentException("frame grab fail");
            }

            int result = webClientService.getDetectedPersons(imageBytes);
            liveCongestionService.calculateCongestion(result, cctv.getId());
        }
    }
}
