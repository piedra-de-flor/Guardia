package com.capstone.capstonedesign.web.controller.protect.target;

import com.capstone.capstonedesign.dto.protect.target.*;
import com.capstone.capstonedesign.service.protect.target.ProtectedTargetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        return ResponseEntity.ok(service.create(memberEmail, createDto, image));
    }

    @Operation(summary = "보호 대상 단일 조회", description = "자신 소유의 보호 대상을 단일 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/protected-target")
    public ResponseEntity<ProtectedTargetReadDto> read(
            @Parameter(description = "보호 대상 단일 조회 요청값", required = true)
            @RequestParam long protectedTargetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        return ResponseEntity.ok(service.read(memberEmail, protectedTargetId));
    }

    @Operation(summary = "보호 대상 전체 조회", description = "자신 소유의 보호 대상을 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/protected-targets")
    public ResponseEntity<ProtectedTargetReadAllDto> readAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        return ResponseEntity.ok(service.readAll(memberEmail));
    }

    @Operation(summary = "보호 대상 수정", description = "자신 소유의 보호 대상을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PatchMapping("/protected-target")
    public ResponseEntity<ProtectedTargetUpdateDto> update(
            @Parameter(description = "보호 대상 전체 조회 요청값", required = true)
            @RequestBody ProtectedTargetUpdateDto updateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        return ResponseEntity.ok(service.update(memberEmail, updateDto));
    }

    @Operation(summary = "보호 대상 삭제", description = "자신 소유의 보호 대상을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @DeleteMapping("/protected-target")
    public ResponseEntity<Long> delete(
            @Parameter(description = "보호 대상 삭제 요청값", required = true)
            @RequestBody ProtectedTargetDeleteDto deleteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName();

        return ResponseEntity.ok(service.delete(memberEmail, deleteDto));
    }
}
