package com.example.restaurantservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String role;    // "system", "user", or "assistant"
    private String content;

    public static ChatMessage of(String role, String content) {
        return new ChatMessage(role, content);
    }
}
