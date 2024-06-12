package com.capstone.capstonedesign.dto.congestion;

import java.util.List;
import java.util.Map;

public record CongestionAPIResponseDto(
        List<Map<String, Object>> results
) {}

