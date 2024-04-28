package com.capstone.capstonedesign.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CongestionStatus {
    CONFUSION("혼잡"),
    COMMON("보통"),
    NOT_MANY_PEOPLE("여유");

    private final String status;
}

;