package com.example.restaurantservice.controller.chat;

import com.example.restaurantservice.dto.request.ChatRequestDto;
import com.example.restaurantservice.dto.response.ChatResponseDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.restaurantservice.service.chat.ChatBotService;

import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/restaurant/owner/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatBotService chatBotService;

    @PostMapping("/webresponse")
    public ChatResponseDto chatWebResponse(@RequestBody ChatRequestDto request,
                                           @RequestParam(name = "sessionId", required = false) String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = "default-session";
        }
        String answer = chatBotService.askQuestion(sessionId, request.getQuestion());
        return new ChatResponseDto(answer);
    }
}
