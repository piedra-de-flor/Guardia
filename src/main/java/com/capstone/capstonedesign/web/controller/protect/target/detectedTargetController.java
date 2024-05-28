package com.capstone.capstonedesign.web.controller.protect.target;

import com.capstone.capstonedesign.service.protect.target.DetectedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "보호 대상 추적 Controller", description = "Protected target detect API")
@RequiredArgsConstructor
@RestController
public class detectedTargetController {
    private final DetectedService service;

    @Operation(summary = "보호 대상 단일 조회", description = "자신 소유의 보호 대상을 단일 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/detect")
    public ResponseEntity<byte[]> detect(
            @Parameter(description = "보호 대상 추적 요청값", required = true)
            @RequestParam long targetId) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        MultipartFile response = service.detect(memberEmail, targetId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", response.getOriginalFilename());

        return new ResponseEntity<>(response.getBytes(), headers, HttpStatus.OK);
    }
}
