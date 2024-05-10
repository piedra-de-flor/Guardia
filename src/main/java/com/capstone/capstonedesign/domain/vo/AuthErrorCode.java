package com.capstone.capstonedesign.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{
    AUTH_ERROR_CODE(HttpStatus.FORBIDDEN, "deny access (JWT Token error)");

    private final HttpStatus httpStatus;
    private final String message;
}
