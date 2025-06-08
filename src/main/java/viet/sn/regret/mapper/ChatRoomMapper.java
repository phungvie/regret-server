package viet.sn.regret.mapper;

import org.mapstruct.Mapper;
import viet.sn.regret.dto.response.ChatRoomResponse;
import viet.sn.regret.entity.room.ChatRoom;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
//    Profile toChatRoom(RegistrationRequest request);
    ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom);
}
