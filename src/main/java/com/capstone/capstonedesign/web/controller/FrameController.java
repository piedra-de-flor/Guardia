package com.capstone.capstonedesign.web.controller;

import com.capstone.capstonedesign.service.FrameGrabber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FrameController {
    private final FrameGrabber frameGrabber;

    @GetMapping("/frame")
    public void getFrame() throws IOException {
        frameGrabber.frameGrab();
    }
}
