package com.capstone.capstonedesign.service.support;

import com.capstone.capstonedesign.domain.vo.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileManager {
    //private static final String BASE_PATH = "/root/capstone-design/protected-image";
    private static final String BASE_PATH = "C:/Users/USER/Desktop/protected_target";

    public Image uploadImage(MultipartFile image) {
        String originName = image.getOriginalFilename();
        String changedName = changedImageName(originName);
        String storedImagePath = createDirPath(changedName);
        log.info("storedImagePath = " + storedImagePath);

        try {
            image.transferTo(new File(storedImagePath));
        } catch (IOException e){
            throw new IllegalArgumentException("이미지 업로드 실패");
        }
        return new Image(originName, storedImagePath);
    }

    public void deleteExistingImage(String imagePath) {
        File existingImage = new File(imagePath);
        if (existingImage.exists()) {
            existingImage.delete();
        }
    }

    private String changedImageName(String originName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        return originName + current_date + Math.random();
    }

    private String createDirPath(String changedName) {
        return BASE_PATH + "/" +changedName;
    }

    public byte[] loadImageAsResource(String imagePath) {
        try {
            log.info("Loading image from path: " + imagePath);
            Path path = Paths.get(imagePath).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                log.info("Resource exists, reading bytes");
                return Files.readAllBytes(path);
            } else {
                log.error("File not found: " + imagePath);
                throw new FileNotFoundException("File not found: " + imagePath);
            }
        } catch (MalformedURLException e) {
            log.error("Malformed URL: " + imagePath, e);
            throw new IllegalArgumentException("이미지를 찾을 수 없습니다.", e);
        } catch (IOException e) {
            log.error("IO Exception while processing image: " + imagePath, e);
            throw new IllegalArgumentException("이미지 처리하는 과정에서 오류가 발생하였습니다.", e);
        }
    }
}
