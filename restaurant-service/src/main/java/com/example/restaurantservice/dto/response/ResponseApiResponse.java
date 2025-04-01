package com.example.restaurantservice.dto.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseApiResponse {
    private String id;
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("output")
    private List<Choice> choices;

    private Usage usage;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private String id;
        private String type;
        private String status;
        private String role;
        private List<ContentItem> content;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentItem {
        private String type;
        private String text;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {

        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;

        @JsonProperty("input_tokens")
        private int inputTokens;

        @JsonProperty("input_tokens_details")
        private Map<String, Object> inputTokensDetails;

        @JsonProperty("output_tokens_details")
        private Map<String, Object> outputTokensDetails;
    }
}
