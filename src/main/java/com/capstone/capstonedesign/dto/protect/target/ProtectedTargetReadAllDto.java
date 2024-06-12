package com.capstone.capstonedesign.dto.protect.target;

import java.util.List;

public record ProtectedTargetReadAllDto(
        List<ProtectedTargetReadDto> protectedTargetReadDtos
) {
}
