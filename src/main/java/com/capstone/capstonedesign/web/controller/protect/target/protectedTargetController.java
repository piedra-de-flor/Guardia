package com.capstone.capstonedesign.web.controller.protect.target;

import com.capstone.capstonedesign.dto.protect.target.ProtectedTargetCreateDto;
import com.capstone.capstonedesign.dto.protect.target.ProtectedTargetIdDto;
import com.capstone.capstonedesign.service.protect.target.ProtectedTargetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "보호 대상 Controller", description = "Protected target API")
@RestController
public class protectedTargetController {
    private final ProtectedTargetService service;

    @Operation(summary = "보호 대상 등록", description = "새로운 보호 대상을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/protected-target")
    public ResponseEntity<ProtectedTargetIdDto> create(
            @Parameter(description = "보호 대상 정보", required = true)
            @RequestPart @Validated ProtectedTargetCreateDto createDto,
            @RequestPart @Validated MultipartFile image) {
        return ResponseEntity.ok(service.create(createDto, image));
    }
}
