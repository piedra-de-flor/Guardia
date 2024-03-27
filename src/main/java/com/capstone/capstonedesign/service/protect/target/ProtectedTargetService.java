package com.capstone.capstonedesign.service.protect.target;

import com.capstone.capstonedesign.domain.entity.Member;
import com.capstone.capstonedesign.domain.entity.ProtectedTarget;
import com.capstone.capstonedesign.domain.vo.Image;
import com.capstone.capstonedesign.dto.protect.target.*;
import com.capstone.capstonedesign.repository.MemberRepository;
import com.capstone.capstonedesign.repository.ProtectedTargetRepository;
import com.capstone.capstonedesign.service.support.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ProtectedTargetService {
    private final ProtectedTargetRepository repository;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;

    public ProtectedTargetIdDto create(ProtectedTargetCreateDto createDto, MultipartFile file) {
        Member member = memberRepository.findById(createDto.memberId())
                .orElseThrow(NoSuchElementException::new);

        Image image = fileManager.uploadImage(file);

        ProtectedTarget protectedTarget = ProtectedTarget.builder()
                .member(member)
                .name(createDto.name())
                .age(createDto.age())
                .image(image)
                .build();

        ProtectedTarget savedProtectedTarget = repository.save(protectedTarget);
        return new ProtectedTargetIdDto(savedProtectedTarget.getId());
    }

    public ProtectedTargetReadDto read(ProtectedTargetReadRequestDto readRequestDto) {
        Member member = memberRepository.findById(readRequestDto.memberId())
                .orElseThrow(NoSuchElementException::new);
        ProtectedTarget protectedTarget = repository.findById(readRequestDto.protectedTargetId())
                        .orElseThrow(NoSuchElementException::new);

        byte[] image = fileManager.loadImageAsResource(protectedTarget.getImage().getStoredFileName());

        return new ProtectedTargetReadDto(protectedTarget.getId(), protectedTarget.getName(), protectedTarget.getAge(), image);
    }

    public ProtectedTargetReadAllDto readAll(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoSuchElementException::new);

        List<ProtectedTarget> protectedTargets = member.getProtectedTargets();
        List<ProtectedTargetReadDto> protectedTargetReadDtos = new ArrayList<>();

        for (ProtectedTarget protectedTarget : protectedTargets) {
            byte[] image = fileManager.loadImageAsResource(protectedTarget.getImage().getStoredFileName());
            protectedTargetReadDtos.add(new ProtectedTargetReadDto(protectedTarget.getId(), protectedTarget.getName(), protectedTarget.getAge(), image));
        }

        return new ProtectedTargetReadAllDto(protectedTargetReadDtos);
    }
}
