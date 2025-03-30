package com.example.restaurantservice.dto.response;

import com.example.restaurantservice.dto.ChatMessage;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private Long created;
    private List<Choice> choices;
    private Usage usage;

    @Data
    public static class Choice {
        private int index;
        private ChatMessage message;
        private String finish_reason;
    }

    @Data
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}