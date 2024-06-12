package com.capstone.capstonedesign.repository;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CCTVRepository extends JpaRepository<CCTV, Long> {
}
