package com.example.restaurantservice.service.chat;



import com.example.restaurantservice.dto.ChatMessage;
import com.example.restaurantservice.dto.request.ResponseApiRequest;
import com.example.restaurantservice.dto.response.ResponseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatBotService {
    private final WebClient openAiResponseWebClient;

    public ChatBotService(@Qualifier("openAiResponseWebClient") WebClient openAiResponseWebClient) {
        this.openAiResponseWebClient = openAiResponseWebClient;
    }

    // 대화 기록 저장 (세션 별)
    private final Map<String, List<ChatMessage>> sessionMessages = new ConcurrentHashMap<>();

    public String askQuestion(String sessionId, String question) {

        if (question == null || question.isEmpty()) {
            return "질문을 입력해주세요.";
        }

        // 기존 대화 기록 로딩
        List<ChatMessage> messages = sessionMessages.computeIfAbsent(sessionId, key -> new ArrayList<>());

        // 첫 대화면 system 메시지 추가 (새 API용 프롬프트)
        if (messages.isEmpty()) {
            messages.add(ChatMessage.of("system", "너는 식당 운영을 도와주는 ai 어시스턴트야. 사장님이 질문하는 내용에 대해 최신 답변을 해줘."));
        }
        // 사용자 메시지 추가
        messages.add(ChatMessage.of("user", question));

        // ResponseApiRequest용 input 생성
        List<ResponseApiRequest.InputItem> inputItems = new ArrayList<>();
        for (ChatMessage msg : messages) {
            ResponseApiRequest.ContentItem contentItem = ResponseApiRequest.ContentItem.builder()
                    .type("input_text")
                    .text(msg.getContent())
                    .build();
            inputItems.add(ResponseApiRequest.InputItem.builder()
                    .role(msg.getRole())
                    .content(List.of(contentItem))
                    .build());
        }

        // 웹 서치 도구 옵션 추가 (예시)
        Map<String, Object> userLocation = new HashMap<>();
        userLocation.put("type", "approximate");
        userLocation.put("country", "KR");

        ResponseApiRequest.Tool webSearchTool = ResponseApiRequest.Tool.builder()
                .type("web_search_preview")
                .userLocation(userLocation)
                .searchContextSize("medium")
                .build();

        // 새 API용 요청 객체 구성
        ResponseApiRequest requestBody = ResponseApiRequest.builder()
                .model("gpt-4o-mini") // 모델 이름 그대로 사용
                .input(inputItems)
                .text(ResponseApiRequest.TextSettings.builder()
                        .format(ResponseApiRequest.Format.builder().type("text").build())
                        .build())
                .reasoning(new HashMap<>())
                .tools(List.of(webSearchTool))
                .temperature(1.0)
                .maxOutputTokens(2048)
                .topP(1)
                .store(true)
                .build();

        // API 호출 후 원본 JSON 응답 문자열로 받기
        String rawResponse = openAiResponseWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .block();

        // 원본 응답 로그 출력
        System.out.println("RAW RESPONSE: " + rawResponse);

        // JSON 문자열을 ResponseApiResponse 객체로 파싱
        ResponseApiResponse response = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readValue(rawResponse, ResponseApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "응답 파싱 중 에러가 발생했습니다.";
        }

        // null 체크 강화
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "답변을 받지 못했습니다.";
        }

        // output 배열(choices) 중에서 type이 "message"이고 role이 "assistant"인 항목 찾기
        String answer = "";
        for (ResponseApiResponse.Choice choice : response.getChoices()) {
            if ("message".equals(choice.getType()) && "assistant".equals(choice.getRole())
                    && choice.getContent() != null) {
                for (ResponseApiResponse.ContentItem ci : choice.getContent()) {
                    if ("output_text".equals(ci.getType())) {
                        answer = ci.getText();
                        break;
                    }
                }
            }
            if (!answer.isEmpty()) break;
        }
        if (answer == null || answer.trim().isEmpty()) {
            return "답변을 받지 못했습니다.";
        }
        // 대화 기록에 어시스턴트 응답 추가
        messages.add(ChatMessage.of("assistant", answer));

        // 요청 후 세션 초기화 (대화 기록 삭제)
        sessionMessages.remove(sessionId);
        return answer.trim();
    }
}
