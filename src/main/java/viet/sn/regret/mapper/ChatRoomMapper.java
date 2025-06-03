package viet.sn.regret.mapper;

import org.mapstruct.Mapper;
import viet.sn.regret.dto.request.RegistrationRequest;
import viet.sn.regret.dto.response.ChatRoomResponse;
import viet.sn.regret.dto.response.ProfileResponse;
import viet.sn.regret.entity.profile.Profile;
import viet.sn.regret.entity.room.ChatRoom;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
//    Profile toChatRoom(RegistrationRequest request);
    ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom);
}
