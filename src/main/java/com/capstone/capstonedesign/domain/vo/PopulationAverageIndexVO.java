package com.capstone.capstonedesign.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopulationAverageIndexVO {
    LIVE_AVERAGE(0),
    HOURLY_AVERAGE(1);

    private final long index;
}
