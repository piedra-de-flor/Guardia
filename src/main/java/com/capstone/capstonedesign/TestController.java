package com.capstone.capstonedesign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        String[] status = new String[]{"혼잡", "여유", "보통"};
        Random random = new Random();
        int randomNum = random.nextInt(3);
        return status[randomNum];
    }
}
