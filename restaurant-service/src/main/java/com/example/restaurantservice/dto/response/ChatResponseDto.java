package com.example.restaurantservice.dto.response;

import lombok.Getter;

@Getter
public class ChatResponseDto {
    private String answer;
    public ChatResponseDto(String answer) { this.answer = answer; }
    // getter
}


