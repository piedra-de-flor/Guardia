package com.capstone.capstonedesign.dto.protect.target;

public record ProtectedTargetCreateDto(
        long memberId,
        String name,
        int age
) {
}
