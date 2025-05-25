package viet.moba.regret.entity.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String profileId;
    // UserId from keycloak
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Status status;
}
