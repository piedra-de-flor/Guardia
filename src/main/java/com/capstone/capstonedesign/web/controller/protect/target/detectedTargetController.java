package com.capstone.capstonedesign.web.controller.protect.target;

import com.capstone.capstonedesign.service.protect.target.DetectedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class detectedTargetController {
    private final DetectedService service;

    @PostMapping("/detect")
    public MultipartFile detect(@RequestBody long targetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        MultipartFile response = service.detect(memberEmail, targetId);
        return response;
    }
}
