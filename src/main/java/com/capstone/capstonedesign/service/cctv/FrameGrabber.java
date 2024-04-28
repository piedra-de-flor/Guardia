package com.capstone.capstonedesign.service.cctv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FrameGrabber {
    String filePath;
    public String frameGrab() throws IOException {
        // Load OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Video capture object
        VideoCapture capture = new VideoCapture();

        // RTSP stream connection
        if (!capture.open("rtsp://172.20.10.2:8888/h264.sdp")) {
            throw new IOException("Error: Couldn't open RTSP stream.");
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
            filePath = "captured_frame.png";

            // Save the frame as a PNG file
            Imgcodecs.imwrite(filePath, frame);

            System.out.println("Frame captured and saved as: " + filePath);
        } else {
            System.out.println("Error: Couldn't read frame from RTSP stream.");
        }

        // Release video capture
        capture.release();
        return filePath;
    }
}