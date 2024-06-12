package com.capstone.capstonedesign.web.controller.support;

import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FrameController {
    private final FrameGrabber frameGrabber;

    @GetMapping("/frame")
    public void getFrame() {
        try {
            frameGrabber.frameGrab();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }
}
