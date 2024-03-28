package com.capstone.capstonedesign.dto.protect.target;

public record ProtectedTargetUpdateDto(
        long memberId,
        long protectedTargetId,
        String name,
        String age
        //TODO
        //fixMe
        //- 이미지 수정하는 로직 필요함
) {
}
