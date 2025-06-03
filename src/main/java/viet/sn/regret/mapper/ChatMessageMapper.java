package viet.sn.regret.mapper;

import org.mapstruct.Mapper;
import viet.sn.regret.dto.response.ChatMessageResponse;
import viet.sn.regret.dto.response.ChatRoomResponse;
import viet.sn.regret.entity.chat.ChatMessage;
import viet.sn.regret.entity.room.ChatRoom;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatRoom);
}
