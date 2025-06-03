package viet.sn.regret.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ChatRoomResponse {
    String id;
    String chatId;//usernameSender_usernameRecipient
    String senderId;//usernameSender
    String recipientId;//usernameRecipient
    String recipientName;//usernameRecipient
}
