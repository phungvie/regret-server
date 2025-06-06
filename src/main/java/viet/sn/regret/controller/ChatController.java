package viet.sn.regret.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import viet.sn.regret.dto.ApiResponse;
import viet.sn.regret.dto.event.ChatNotification;
import viet.sn.regret.dto.response.ChatMessageResponse;
import viet.sn.regret.dto.response.ChatRoomResponse;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.services.ChatMessageService;
import viet.sn.regret.services.ChatRoomService;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatController {
    final SimpMessagingTemplate simpMessagingTemplate;
    final ChatMessageService chatMessageService;
    final ChatRoomService chatRoomService;

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
                        savedMsg.getContent(),
                        savedMsg.getTimestamp()
                )
        );
    }
    @PostMapping("/chat")
    @ResponseBody
    public void processMessage2(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent(),
                        savedMsg.getTimestamp()
                )
        );
    }


    @GetMapping("/messages/{senderId}/{recipientId}")
    @ResponseBody
    public ApiResponse<List<ChatMessageResponse>> findChatMessages(@PathVariable String senderId,
                                                                   @PathVariable String recipientId) {
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .result(chatMessageService.findChatMessages(senderId, recipientId))
                .build();
    }

    @GetMapping("/my-chat-rooms")
    @ResponseBody
    public ApiResponse<List<ChatRoomResponse>> findMyChatRooms() {
        return ApiResponse.<List<ChatRoomResponse>>builder()
                .result(chatRoomService.findMyChatRooms())
                .build();

    }

}
