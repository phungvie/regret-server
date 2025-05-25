package viet.moba.regret.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import viet.moba.regret.entity.room.ChatRoom;
import viet.moba.regret.repository.ChatRoomRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomService {
    final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ){
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId)
                .map(ChatRoom::getId)
                .or(() -> {
                    if(createNewRoomIfNotExists){
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }
                    return  Optional.empty();
                })
                ;
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId= String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient=ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatRoom recipientSender=ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return  chatId;
    }
}
