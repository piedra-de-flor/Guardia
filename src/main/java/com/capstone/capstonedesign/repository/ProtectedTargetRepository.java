package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.protectedTarget.ProtectedTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtectedTargetRepository extends JpaRepository<ProtectedTarget, Long> {
}
