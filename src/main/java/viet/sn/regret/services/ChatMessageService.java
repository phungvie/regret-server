package viet.sn.regret.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import viet.sn.regret.dto.response.ChatMessageResponse;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.mapper.ChatMessageMapper;
import viet.sn.regret.repository.ChatMessageRepository;
import viet.sn.regret.repository.ProfileRepository;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    final ChatMessageRepository chatMessageRepository;
    final ChatRoomService chatRoomService;
    final ProfileRepository profileRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setTimestamp(Date.from(Instant.now()));
        chatMessage.setSenderId(profileRepository.findByUserId(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow().getProfileId());
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageResponse> findChatMessages(String sendId, String recipientId) {
        var chatId =chatRoomService.getChatRoomId(sendId,recipientId,false);
        return chatId.map(id -> chatMessageRepository
                .findByChatId(id)
                .stream()
                .map(chatMessageMapper::toChatMessageResponse)
                .toList()
        ).orElse(new ArrayList<>());
    }


}
