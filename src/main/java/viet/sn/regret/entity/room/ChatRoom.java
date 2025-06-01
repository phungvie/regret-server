package viet.sn.regret.entity.room;

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

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String chatId;//usernameSender_usernameRecipient
    String senderId;//usernameSender
    String recipientId;//usernameRecipient
}
