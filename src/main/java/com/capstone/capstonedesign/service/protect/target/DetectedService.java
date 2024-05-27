package com.capstone.capstonedesign.service.protect.target;

import com.capstone.capstonedesign.domain.entity.protectedTarget.ProtectedTarget;
import com.capstone.capstonedesign.repository.ProtectedTargetRepository;
import com.capstone.capstonedesign.service.ai.webClinet.WebClientService;
import com.capstone.capstonedesign.service.cctv.FrameGrabber;
import com.capstone.capstonedesign.service.support.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class DetectedService {
    private final FileManager fileManager;
    private final WebClientService webClientService;
    private final FrameGrabber frameGrabber;
    private final ProtectedTargetRepository protectedTargetRepository;

    public MultipartFile detect(String memberEmail, long protectedTargetId) {
        ProtectedTarget target = protectedTargetRepository.findById(protectedTargetId)
                .orElseThrow(NoSuchElementException::new);

        if (!target.getMember().getEmail().equals(memberEmail)) {
            throw new IllegalArgumentException("tou dont have auth for the target");
        }

        byte[] targetImage = fileManager.loadImageAsResource(target.getImage().getStoredFileName());
        byte[] cctvImage;
        try {
            cctvImage = frameGrabber.frameGrab();
        } catch (IOException e) {
            throw new IllegalArgumentException("CCTV frame catch failed", e);
        }
        try {
            return webClientService.detectTarget(cctvImage, targetImage);
        } catch (IOException e) {
            throw new IllegalArgumentException("detect ai is something wrong");
        }
    }
}
