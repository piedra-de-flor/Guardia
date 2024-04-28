package com.capstone.capstonedesign.web.controller.congestion;

import com.capstone.capstonedesign.service.congestion.CongestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        service.test1(pop);
    }

    @GetMapping("/test2/{pop}")
    public void test2(@PathVariable int pop) {
        service.test2(pop);
    }
}
