package com.capstone.capstonedesign.service.cctv;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class FrameGrabber {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static final String RTSP_STREAM_URL = "rtsp://192.168.219.111:8888/h264.sdp";
    private final VideoCapture capture;
    private final AtomicBoolean isStreaming = new AtomicBoolean(false);

    public FrameGrabber() {
        this.capture = new VideoCapture();
        this.capture.open(RTSP_STREAM_URL);
        capture.set(Videoio.CAP_PROP_BUFFERSIZE, 1);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        capture.set(Videoio.CAP_PROP_FPS, 60);
    }

    public void startStreaming() throws IOException {
        if (!capture.isOpened()) {
            throw new IOException("Error: Couldn't open RTSP stream.");
        }
        isStreaming.set(true);
    }

    public void stopStreaming() {
        isStreaming.set(false);
        if (capture.isOpened()) {
            capture.release();
        }
    }

    public byte[] getMJPEGFrame() throws IOException {
        if (!isStreaming.get()) {
            throw new IllegalStateException("Streaming has not been started.");
        }

        Mat frame = new Mat();
        if (capture.read(frame)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", frame, matOfByte);
            baos.write(matOfByte.toArray());

            return baos.toByteArray();
        } else {
            throw new IOException("Error: Couldn't read frame from RTSP stream.");
        }
    }

    public byte[] frameGrab() throws IOException {
        VideoCapture capture = new VideoCapture();

        if (!capture.open("rtsp://192.168.219.111:8888/h264.sdp")) {
            throw new IOException("Error: Couldn't open RTSP stream.");
        }

        Mat frame = new Mat();

        if (capture.read(frame)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".png", frame, matOfByte);
            baos.write(matOfByte.toArray());
            capture.release();
            return baos.toByteArray();
        } else {
            capture.release();
            throw new IOException("Error: Couldn't read frame from RTSP stream.");
        }
    }
}
