package viet.sn.regret.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import viet.sn.regret.dto.request.ChatMessageRequest;
import viet.sn.regret.dto.response.ChatRoomResponse;
import viet.sn.regret.entity.profile.Profile;
import viet.sn.regret.entity.room.ChatRoom;
import viet.sn.regret.exception.AppException;
import viet.sn.regret.exception.ErrorCode;
import viet.sn.regret.mapper.ChatRoomMapper;
import viet.sn.regret.repository.ChatRoomRepository;
import viet.sn.regret.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomService {
    final ChatRoomRepository chatRoomRepository;
    final ChatRoomMapper chatRoomMapper;
    final ProfileRepository profileRepository;

    public List<String> getChatRoomId(ChatMessageRequest chatMessageRequest, boolean createNewRoomIfNotExists) {
        List<String> result = new ArrayList<>();

        List<String> targets = chatMessageRequest.getTargets();
        if (targets == null || targets.isEmpty()) {
            return result;
        }

        for (String rawId : targets) {
            String trimmedId = rawId.trim();

            Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(trimmedId);
            if (chatRoomOpt.isPresent()) {
                result.add(chatRoomOpt.get().getId());
                continue;
            }

            Optional<Profile> profileOpt = profileRepository.findById(trimmedId);//đoạn này sau này kiểm tra xem có được phép gửi tin nhắn đến user đó không
            if (profileOpt.isEmpty()) {
                continue;
            }

            if (createNewRoomIfNotExists) {
                result.add(chatRoomRepository.save(
                        ChatRoom.builder()
                        .name(profileOpt.get().getUsername())
                        .build()
                ).getId());
            }
        }
        return result;
    }


    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);
        if (senderId.trim().equals(recipientId.trim())) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();
            chatRoomRepository.save(chatRoom);
            return chatId;
        }

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }

    public List<ChatRoomResponse> findMyChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySenderId(profileRepository.findByUserId(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow().getProfileId());
        return chatRooms.stream().map(chatRoom -> {
            ChatRoomResponse chatRoomResponse = chatRoomMapper.toChatRoomResponse(chatRoom);
            chatRoomResponse.setRecipientName(profileRepository.findById(chatRoomResponse.getRecipientId()).orElseThrow(() -> new AppException(ErrorCode.profile_exist)).getUsername());
            return chatRoomResponse;
        }).toList();
    }
}
