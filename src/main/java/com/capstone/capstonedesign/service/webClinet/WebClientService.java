package com.capstone.capstonedesign.service.webClinet;

import com.capstone.capstonedesign.dto.congestion.CongestionAPIResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WebClientService {
    private final WebClient webClient;

    public int getDetectedPersons(byte[] imageBytes) {
        try {
            ByteArrayResource imageResource = new ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() {
                    return "image.jpg";
                }
            };

            MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
            bodyMap.add("file", imageResource);

            Mono<CongestionAPIResponseDto> responseMono = webClient.post()
                    .uri("/congestion")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyMap))
                    .retrieve()
                    .bodyToMono(CongestionAPIResponseDto.class);

            CongestionAPIResponseDto response = responseMono.block();

            if (response != null && response.results() != null) {
                List<Map<String, Object>> results = response.results();
                if (!results.isEmpty() && results.get(0).containsKey("detected_persons")) {
                    return (int) results.get(0).get("detected_persons");
                }
            }
            return 0;
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
