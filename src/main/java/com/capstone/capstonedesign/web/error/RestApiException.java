package com.capstone.capstonedesign.web.error;

import com.capstone.capstonedesign.domain.vo.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
