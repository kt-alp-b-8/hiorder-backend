package com.example.restaurantservice.dto.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseApiRequest {
    private String model;
    private List<InputItem> input; // 기존 messages 대신 사용

    private TextSettings text;
    private Map<String, Object> reasoning;
    private List<Tool> tools;
    private Double temperature;

    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;

    @JsonProperty("top_p")
    private Integer topP;

    private Boolean store;

    @Data
    @Builder
    public static class InputItem {
        private String role; // system, user, assistant
        private List<ContentItem> content;
    }

    @Data
    @Builder
    public static class ContentItem {
        private String type; // 예: input_text, output_text
        private String text;
    }

    @Data
    @Builder
    public static class TextSettings {
        private Format format;
    }

    @Data
    @Builder
    public static class Format {
        private String type; // 예: text
    }

    @Data
    @Builder
    public static class Tool {
        private String type; // 예: web_search_preview

        @JsonProperty("user_location")
        private Map<String, Object> userLocation;

        @JsonProperty("search_context_size")
        private String searchContextSize; // 예: medium
    }
}
