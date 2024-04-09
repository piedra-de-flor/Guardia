package com.capstone.capstonedesign.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FrameGrabber {
    public void frameGrab() throws IOException {
        // Load OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Video capture object
        VideoCapture capture = new VideoCapture();

        // RTSP stream connection
        if (!capture.open("rtsp://192.168.219.112:8888/h264.sdp")) {
            System.out.println("Error: Couldn't open RTSP stream.");
            return;
        }

        // Frame width and height
        int frameWidth = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
        int frameHeight = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
        System.out.println("Frame Width: " + frameWidth + ", Frame Height: " + frameHeight);

        // Mat object to store frame
        Mat frame = new Mat();

        // Capture a frame
        if (capture.read(frame)) {
            // Define the file path and name
            String filePath = "captured_frame.png";

            // Save the frame as a PNG file
            Imgcodecs.imwrite(filePath, frame);

            System.out.println("Frame captured and saved as: " + filePath);
        } else {
            System.out.println("Error: Couldn't read frame from RTSP stream.");
        }

        // Release video capture
        capture.release();
    }
}