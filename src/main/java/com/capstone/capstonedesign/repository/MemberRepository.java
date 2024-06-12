package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.membership.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
