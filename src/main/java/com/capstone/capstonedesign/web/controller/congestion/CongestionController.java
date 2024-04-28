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

    @GetMapping("/test2/{pop}")
    public void test2(@PathVariable int pop) {
        log.info("test1");
        service.test1(pop);
        service.testForTime(pop);
        service.testForDay(pop);
        service.testForMonth(pop);
    }

    @GetMapping("/testForTime/{pop}")
    public void testForTime(@PathVariable int pop) {
        log.info("test2");
        service.testForTime(pop);
    }

    @GetMapping("/testForDay/{pop}")
    public void testForDay(@PathVariable int pop) {
        log.info("test3");
        service.testForDay(pop);
    }

    @GetMapping("/testForMonth/{pop}")
    public void testForMonth(@PathVariable int pop) {
        log.info("test4");
        service.testForMonth(pop);
    }
}
