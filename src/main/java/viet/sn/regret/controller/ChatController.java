package viet.sn.regret.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import viet.sn.regret.dto.ApiResponse;
import viet.sn.regret.dto.event.ChatNotification;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.services.ChatMessageService;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatController {
    final SimpMessagingTemplate simpMessagingTemplate;
    final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
    }
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ApiResponse<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                                                           @PathVariable String recipientId) {
        return ApiResponse.<List<ChatMessage>>builder()
                .result(chatMessageService.findChatMessages(senderId, recipientId))
                .build();
    }

}
