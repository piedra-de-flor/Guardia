/*
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
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        image = resizeImage(image, 640, 640);
        int width = image.getWidth();
        int height = image.getHeight();

        float[][][][] pixelValues = new float[1][3][height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                float red = ((rgb >> 16) & 0xFF) / 255.0f;
                float green = ((rgb >> 8) & 0xFF) / 255.0f;
                float blue = (rgb & 0xFF) / 255.0f;


                // RGB 값을 배열에 저장
                pixelValues[0][0][y][x] = red;
                pixelValues[0][1][y][x] = green;
                pixelValues[0][2][y][x] = blue;
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
*/
