package com.capstone.capstonedesign.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CongestionStatus {
    CONFUSION("confusion"),
    COMMON("normal"),
    NOT_MANY_PEOPLE("no many people");

    private final String status;
}

;