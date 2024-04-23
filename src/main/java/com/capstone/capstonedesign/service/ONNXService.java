/*
package com.capstone.capstonedesign.service;

import ai.onnxruntime.*;
import com.capstone.capstonedesign.service.support.ImageToFloatArrayConverter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ONNXService {
    private OrtSession session;
    private OrtEnvironment env;

    public ONNXService() {
        try {
            Path modelPath = Paths.get("C:\\Users\\USER\\Desktop\\공부\\capstone-design\\src\\main\\resources\\best.onnx");

            env = OrtEnvironment.getEnvironment();

            session = env.createSession(modelPath.toString());
            System.out.println("onnx 파일 연동 성공");
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    // ONNX 모델 실행
    public void predict() {
        String imagePath = "C:\\Users\\USER\\Desktop\\공부\\capstone-design\\src\\main\\resources\\onnxTest.jpg";

        float[][][][] inputData = ImageToFloatArrayConverter.convertImageToFloatArray(imagePath);
        System.out.println(inputData.length + "," + inputData[0].length + "," + inputData[0][0].length + "," + inputData[0][0][0].length);
        try {
            OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);

            Map<String, OnnxTensor> inputs = new HashMap<>();
            inputs.put("images", inputTensor);
            OrtSession.Result results = session.run(inputs);
            System.out.println(results.get(0));

            processResult((OnnxTensor) results.get(0));

        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    public static void processResult(OnnxTensor resultTensor) {
        try {
            TensorInfo tensorInfo = resultTensor.getInfo();
            long[] shape = tensorInfo.getShape();

            // 결과 텐서의 데이터 얻기
            float[][][] resultData = (float[][][]) resultTensor.getValue();

            // 결과 텐서의 데이터 처리
            // 여기서는 단순히 출력해 보겠습니다.
            System.out.println("Result Shape: " + Arrays.toString(shape));
            System.out.println("Result Data: ");
            for (float[][] array3D : resultData) {
                for (float[] array2D : array3D) {
                    for (float array1D : array2D) {
                        System.out.print(array1D);
                    }
                }
            }

            applyThreshold(resultData, 0.5f, "C:\\Users\\USER\\Desktop\\공부\\output.png");
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    public static void applyThreshold(float[][][] prediction, float threshold, String outputPath) {
        int height = prediction.length;
        int width = prediction[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Iterate over each pixel in the prediction array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Check if the prediction value at this pixel exceeds the threshold
                if (prediction[y][x][0] >= threshold) {
                    // Set pixel color based on the thresholding condition (for example, white color)
                    int rgb = 0xFFFFFF; // White color
                    image.setRGB(x, y, rgb);
                } else {
                    // Set pixel color for values below the threshold (for example, black color)
                    int rgb = 0x000000; // Black color
                    image.setRGB(x, y, rgb);
                }
            }
        }

        // Save the generated image to the specified output path
        try {
            File output = new File(outputPath);
            ImageIO.write(image, "png", output);
            System.out.println("Thresholding applied. Image saved to: " + outputPath);
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    public void closeSession() {
        try {
            session.close();
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }
}*/
