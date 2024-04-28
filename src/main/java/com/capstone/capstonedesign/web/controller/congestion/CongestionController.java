package com.capstone.capstonedesign.web.controller.congestion;

import com.capstone.capstonedesign.service.congestion.CongestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CongestionController {
    private final CongestionService service;

    @GetMapping("/test/{pop}")
    public void test(@PathVariable int pop) {
        service.test(pop);
    }

    @GetMapping("/test1/{pop}")
    public void test1(@PathVariable int pop) {
        log.info("test1");
        service.test1(pop);
        service.testForTime(pop);
        service.testForDay(pop);
        service.testForMonth(pop);
    }
}
