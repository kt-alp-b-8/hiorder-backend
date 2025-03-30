package com.example.restaurantservice.service.translation;

import com.example.restaurantservice.dto.ChatMessage;
import com.example.restaurantservice.dto.request.ChatCompletionRequest;
import com.example.restaurantservice.dto.response.ChatCompletionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TranslationService {

    private final WebClient openAiWebClient;

    // 번역(한글 -> 영어) 예시
    public String translateToEnglish(String krText) {
        if (krText == null || krText.isEmpty()) return "";

        // 1) ChatGPT에 전달할 메시지
        // system / user / assistant 역할 중, 여기서는 user 프롬프트만 예시
        ChatCompletionRequest requestBody = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(
                        ChatMessage.of("system", "You are a helpful translator."),
                        ChatMessage.of("user", "I'm making a kiosk app in Korea now. I need to translate the Korean text of [menu names, menu descriptions, and menu category names] to English for my American customer. Please translate this korean text to English.Please do not return more than 40 characters:\n" + krText)
                ))
                .maxTokens(1000)
                .temperature(0.0)
                .build();

        // 2) API 호출
        ChatCompletionResponse response = openAiWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block(); // block() for sync example

        if (response == null || response.getChoices().isEmpty()) {
            return "";
        }

        // 3) 응답 파싱
        String content = response.getChoices().get(0).getMessage().getContent();
        return content != null ? content.trim() : "";
    }
    public String translateToChinese(String krText) {
        if (krText == null || krText.isEmpty()) return "";

        // 1) ChatGPT에 전달할 메시지
        // system / user / assistant 역할 중, 여기서는 user 프롬프트만 예시
        ChatCompletionRequest requestBody = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(
                        ChatMessage.of("system", "You are a helpful translator."),
                        ChatMessage.of("user", "I'm making a kiosk app in Korea now. I need to translate the Korean text of [menu names, menu descriptions, and menu category names] to Chinese for my Chinese customer. Please translate this korean text to Chinese.Please do not return more than 40 characters:\n" + krText)
                ))
                .maxTokens(1000)
                .temperature(0.0)
                .build();

        // 2) API 호출
        ChatCompletionResponse response = openAiWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block(); // block() for sync example

        if (response == null || response.getChoices().isEmpty()) {
            return "";
        }

        // 3) 응답 파싱
        String content = response.getChoices().get(0).getMessage().getContent();
        return content != null ? content.trim() : "";
    }

    public String translateToJapanese(String krText) {
        if (krText == null || krText.isEmpty()) return "";

        // 1) ChatGPT에 전달할 메시지
        // system / user / assistant 역할 중, 여기서는 user 프롬프트만 예시
        ChatCompletionRequest requestBody = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(
                        ChatMessage.of("system", "You are a helpful translator."),
                        ChatMessage.of("user", "I'm making a kiosk app in Korea now. I need to translate the Korean text of [menu names, menu descriptions, and menu category names] to Japanese for my Japanese customer. Please translate this korean text to Japanese.Please do not return more than 40 characters:\n" + krText)
                ))
                .maxTokens(1000)
                .temperature(0.0)
                .build();

        // 2) API 호출
        ChatCompletionResponse response = openAiWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block(); // block() for sync example

        if (response == null || response.getChoices().isEmpty()) {
            return "";
        }

        // 3) 응답 파싱
        String content = response.getChoices().get(0).getMessage().getContent();
        return content != null ? content.trim() : "";
    }
}


