package com.capstone.capstonedesign.web.controller.protect.target;

import com.capstone.capstonedesign.service.protect.target.DetectedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class detectedTargetController {
    private final DetectedService service;

    @GetMapping("/detect")
    public ResponseEntity<byte[]> detect(@RequestParam long targetId) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        MultipartFile response = service.detect(memberEmail, targetId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", response.getOriginalFilename());

        return new ResponseEntity<>(response.getBytes(), headers, HttpStatus.OK);
    }
}
