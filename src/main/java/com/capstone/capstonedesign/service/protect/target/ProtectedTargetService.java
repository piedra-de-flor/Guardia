package com.capstone.capstonedesign.service.protect.target;

import com.capstone.capstonedesign.domain.entity.membership.Member;
import com.capstone.capstonedesign.domain.entity.protectedTarget.ProtectedTarget;
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

    public ProtectedTargetIdDto create(String email, ProtectedTargetCreateDto createDto, MultipartFile file) {
        Member member = memberRepository.findByEmail(email)
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

    public ProtectedTargetReadDto read(String email, long protectedTargetId) {
        ProtectedTarget protectedTarget = isMine(email, protectedTargetId);

        byte[] image = fileManager.loadImageAsResource(protectedTarget.getImage().getStoredFileName());

        return new ProtectedTargetReadDto(protectedTarget.getId(), protectedTarget.getName(), protectedTarget.getAge(), image);
    }

    public ProtectedTargetReadAllDto readAll(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        List<ProtectedTarget> protectedTargets = member.getProtectedTargets();
        List<ProtectedTargetReadDto> protectedTargetReadDtos = new ArrayList<>();

        for (ProtectedTarget protectedTarget : protectedTargets) {
            byte[] image = fileManager.loadImageAsResource(protectedTarget.getImage().getStoredFileName());
            protectedTargetReadDtos.add(new ProtectedTargetReadDto(protectedTarget.getId(), protectedTarget.getName(), protectedTarget.getAge(), image));
        }

        return new ProtectedTargetReadAllDto(protectedTargetReadDtos);
    }

    public ProtectedTargetUpdateDto update(String email, ProtectedTargetUpdateDto updateDto) {
        ProtectedTarget protectedTarget = isMine(email, updateDto.protectedTargetId());
        protectedTarget.update(updateDto.name(), updateDto.age());
        return updateDto;
    }

    public Long delete(String email, ProtectedTargetDeleteDto deleteDto) {
        ProtectedTarget protectedTarget = isMine(email, deleteDto.protectedTargetId());
        repository.delete(protectedTarget);
        fileManager.deleteExistingImage(protectedTarget.getImage().getStoredFileName());
        return protectedTarget.getId();
    }

    private ProtectedTarget isMine(String email, long protectedTargetId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
        ProtectedTarget protectedTarget = repository.findById(protectedTargetId)
                .orElseThrow(NoSuchElementException::new);

        if (protectedTarget.isMine(member)) {
            throw new IllegalArgumentException("본인 소유의 보호 대상이 아닙니다.");
        } else {
            return protectedTarget;
        }
    }
}
