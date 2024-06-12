package com.capstone.capstonedesign.web.controller.cctv;

import com.capstone.capstonedesign.dto.cctv.CCTVCreateDto;
import com.capstone.capstonedesign.service.cctv.CCTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CCTVController {
    private final CCTVService service;

    @PostMapping("/cctv")
    public ResponseEntity<CCTVCreateDto> create(@RequestBody CCTVCreateDto dto) {
        CCTVCreateDto response = service.create(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cctv/{cctvId}")
    public ResponseEntity<Boolean> delete(@PathVariable long cctvId) {
        boolean response = service.delete(cctvId);
        return ResponseEntity.ok(response);
    }
}
