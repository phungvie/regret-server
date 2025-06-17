package viet.sn.regret.entity;

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
public class ChatRoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String profileId;
    String roomId;

}
