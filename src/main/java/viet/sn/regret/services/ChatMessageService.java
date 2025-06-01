package viet.sn.regret.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    final ChatMessageRepository chatMessageRepository;
    final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String sendId,String recipientId) {
        var chatId =chatRoomService.getChatRoomId(sendId,recipientId,false);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }


}
