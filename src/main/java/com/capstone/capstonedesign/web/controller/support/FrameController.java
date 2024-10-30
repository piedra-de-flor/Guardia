package com.capstone.capstonedesign.web.controller.support;

import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

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

    @GetMapping(value = "/stream", produces = MediaType.IMAGE_JPEG_VALUE)
    public void streamVideoAsMJPEG(HttpServletResponse response) {
        response.setContentType("multipart/x-mixed-replace; boundary=frame");
        try (OutputStream out = response.getOutputStream()) {
            frameGrabber.startStreaming();
            while (true) {
                long startTime = System.currentTimeMillis();

                byte[] frame = frameGrabber.getMJPEGFrame();
                if (frame != null) {
                    // Frame boundary 설정
                    out.write(("--frame\r\n").getBytes());
                    out.write("Content-Type: image/jpeg\r\n".getBytes());
                    out.write(("Content-Length: " + frame.length + "\r\n\r\n").getBytes());
                    out.write(frame);
                    out.write("\r\n".getBytes());
                    out.flush();

                    long endTime = System.currentTimeMillis();
                    long timeTaken = endTime - startTime;
                    log.info("Frame send time: {} ms", timeTaken);

                    long delay = 16 - timeTaken;
                    if (delay > 0) {
                        Thread.sleep(delay);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            frameGrabber.stopStreaming();
        }
    }


    // Cleanup when the application is stopped or destroyed
    @PreDestroy
    public void onDestroy() {
        frameGrabber.stopStreaming();
    }
}
