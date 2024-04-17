package com.capstone.capstonedesign.service.support;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageToFloatArrayConverter {
    public static float[][][][] convertImageToFloatArray(String imagePath) {
        BufferedImage image = null;
        try {
            // 이미지 파일 읽기
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        image = resizeImage(image, 640, 640);
        // 이미지의 폭과 높이 가져오기
        int width = image.getWidth();
        int height = image.getHeight();

        // 이미지의 픽셀 값을 저장할 float 배열 생성
        float[][][][] pixelValues = new float[1][3][height][width];

        // 이미지의 픽셀 값을 float 배열에 저장
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 각 픽셀의 RGB 값을 가져와서 float 배열에 저장
                int rgb = image.getRGB(x, y);
                float red = ((rgb >> 16) & 0xFF) / 255.0f;
                float green = ((rgb >> 8) & 0xFF) / 255.0f;
                float blue = (rgb & 0xFF) / 255.0f;

                // 이미지를 그레이스케일로 변환하려면 다음과 같이 사용합니다.
                // float gray = (red + green + blue) / 3.0f;

                // RGB 값을 배열에 저장
                pixelValues[0][2][y][x] = red;
                // pixelValues[1][y][x] = green;
                // pixelValues[2][y][x] = blue;
            }
        }

        return pixelValues;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
