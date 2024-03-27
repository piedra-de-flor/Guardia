package com.capstone.capstonedesign.dto.protect.target;

public record ProtectedTargetReadDto(
        long id,
        String name,
        long age,
        byte[] image
) {
}
