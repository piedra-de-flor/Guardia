package com.capstone.capstonedesign.web.controller.membership;

import com.capstone.capstonedesign.dto.membership.MemberResponseDto;
import com.capstone.capstonedesign.dto.membership.MemberSignUpRequestDto;
import com.capstone.capstonedesign.service.membership.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 Controller", description = "Member API")
@RestController
public class MemberController {
    private final MemberService service;

    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> signUp(
            @Parameter(description = "회원 가입 요청 정보", required = true)
            @RequestBody @Validated final MemberSignUpRequestDto userJoinRequestDto) {
        MemberResponseDto responseDto = service.signUp(userJoinRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
