package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.domain.entity.congestion.Congestion;
import com.capstone.capstonedesign.domain.entity.congestion.LiveCongestion;
import com.capstone.capstonedesign.repository.AveragePopulationRepository;
import com.capstone.capstonedesign.repository.CCTVRepository;
import com.capstone.capstonedesign.repository.CongestionRepository;
import com.capstone.capstonedesign.repository.LiveCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class LiveCongestionService {
    private final CongestionRepository congestionRepository;
    private final LiveCongestionRepository liveCongestionRepository;
    private final AveragePopulationRepository populationRepository;
    private final CongestionCalculator congestionCalculator;
    private final CCTVRepository cctvRepository;

    @Async
    @Transactional
    public void calculateCongestion(int population, long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        Congestion congestion = new Congestion(population, congestionCalculator.calculateCongestionStatus(population), cctv);
        Congestion savedCongestion = congestionRepository.save(congestion);
        populationRepository.getReferenceById(1L).updateAverage(congestion.getPopulation());
        createEmptyLiveCongestion(cctv, savedCongestion);
    }

    private void createEmptyLiveCongestion(CCTV cctv, Congestion congestion) {
        LiveCongestion liveCongestion = null;
        if (liveCongestionRepository.findByCctv(cctv).isEmpty()) {
            liveCongestion = new LiveCongestion(cctv, congestion);
            liveCongestionRepository.save(liveCongestion);
        } else {
            cctv.updateLiveCongestion(liveCongestion, congestion);
        }
    }

    public void testLive(int population, long cctvId) {
        CCTV cctv = cctvRepository.findById(cctvId)
                .orElseThrow(NoSuchElementException::new);
        Congestion congestion = new Congestion(population, congestionCalculator.calculateCongestionStatus(population), cctv);
        Congestion savedCongestion = congestionRepository.save(congestion);
        LiveCongestion liveCongestion = new LiveCongestion(savedCongestion);
        liveCongestionRepository.save(liveCongestion);
    }
}
