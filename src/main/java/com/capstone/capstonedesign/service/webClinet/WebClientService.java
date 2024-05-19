package com.capstone.capstonedesign.service.webClinet;

import com.capstone.capstonedesign.dto.congestion.CongestionAPIResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WebClientService {
    private final WebClient webClient;

    public int getDetectedPersons(byte[] imageBytes) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            Mono<CongestionAPIResponseDto> responseMono = webClient.post()
                    .uri("/congestion")
                    .body(BodyInserters.fromValue(Map.of("file", base64Image)))
                    .retrieve()
                    .bodyToMono(CongestionAPIResponseDto.class);

            CongestionAPIResponseDto response = responseMono.block();

            if (response != null && response.response() != null) {
                Map<String, Object> result = response.response();
                if (result.containsKey("detected_persons")) {
                    return (int) result.get("detected_persons");
                }
            }
            return 0;
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
