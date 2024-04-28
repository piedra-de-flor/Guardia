package com.capstone.capstonedesign.service.congestion;

import com.capstone.capstonedesign.domain.entity.congestion.Congestion;
import com.capstone.capstonedesign.domain.entity.congestion.LiveCongestion;
import com.capstone.capstonedesign.repository.AveragePopulationRepository;
import com.capstone.capstonedesign.repository.CongestionRepository;
import com.capstone.capstonedesign.repository.LiveCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LiveCongestionService {
    private final CongestionRepository congestionRepository;
    private final LiveCongestionRepository liveCongestionRepository;
    private final AveragePopulationRepository populationRepository;
    private final CongestionCalculator congestionCalculator;

    @Transactional
    public void calculateCongestion(int population) {
        Congestion congestion = new Congestion(population, congestionCalculator.calculateCongestionStatus(population));
        Congestion savedCongestion = congestionRepository.save(congestion);
        updateLiveCongestion(savedCongestion);
    }

    private void updateLiveCongestion(Congestion congestion) {
        populationRepository.getReferenceById(1L).updateAverage(congestion.getPopulation());
        LiveCongestion liveCongestion = liveCongestionRepository.getReferenceById(1L);
        liveCongestion.updateStatus(congestion);
    }
}
