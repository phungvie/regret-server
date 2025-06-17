package viet.sn.regret.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import viet.sn.regret.dto.request.ChatMessageRequest;
import viet.sn.regret.dto.response.ChatMessageResponse;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.mapper.ChatMessageMapper;
import viet.sn.regret.repository.ChatMessageRepository;
import viet.sn.regret.repository.ProfileRepository;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    final ChatMessageRepository chatMessageRepository;
    final ChatRoomService chatRoomService;
    final ProfileRepository profileRepository;
    private final ChatMessageMapper chatMessageMapper;

    public List<ChatMessageResponse> save(ChatMessageRequest chatMessageRequest) {
        chatMessageRequest.setTimestamp(Date.from(Instant.now()));
        chatMessageRequest.setSenderId(profileRepository.findByUserId(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow().getProfileId());
        List<String> roomIds = chatRoomService.getChatRoomId(
                chatMessageRequest,
                true
        );
        return chatMessageRepository.saveAll(roomIds.stream().map(s ->
                ChatMessage.builder()
                        .roomId(s)
                        .content(chatMessageRequest.getContent().trim()).build()
        ).toList()).stream().map(chatMessageMapper::toChatMessageResponse).toList();
    }

//    public Page<ChatMessageResponse> findChatMessages(String sendId, String recipientId, int page, int size) {
//        var chatId = chatRoomService.getChatRoomId(sendId, recipientId, false);
//        return chatId.map(id -> chatMessageRepository
//                .findByChatId(id, PageRequest.of(page, size, Sort.by("timestamp").descending()))
//                .map(chatMessageMapper::toChatMessageResponse)
//        ).orElse(Page.empty());
//    }


}
