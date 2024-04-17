package com.capstone.capstonedesign.web.controller;

import com.capstone.capstonedesign.service.ONNXService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OnnxController {
    private final ONNXService service;

    @GetMapping("/test")
    public void test() {
        service.predict();
    }
}
