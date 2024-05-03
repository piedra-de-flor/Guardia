package com.capstone.capstonedesign.service.cctv;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import com.capstone.capstonedesign.dto.cctv.CCTVCreateDto;
import com.capstone.capstonedesign.repository.CCTVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CCTVService {
    private final CCTVRepository repository;

    public CCTVCreateDto create(CCTVCreateDto dto) {
        CCTV cctv = new CCTV(dto.section(), dto.floor());
        CCTV savedCCTV = repository.save(cctv);

        return new CCTVCreateDto(savedCCTV.getSection(), savedCCTV.getFloor());
    }

    public boolean delete(long cctvId) {
        CCTV cctv = repository.findById(cctvId)
                        .orElseThrow(NoSuchElementException::new);
        repository.delete(cctv);
        return true;
    }
}
