package com.capstone.capstonedesign.dto.membership;

import com.capstone.capstonedesign.domain.entity.Member;

public record MemberResponseDto(long userId, String name, String email, String role) {
    public static MemberResponseDto fromUser(Member member) {
        return new MemberResponseDto(member.getId(), member.getNickName(), member.getEmail(), member.getRoles().get(0));
    }
}
