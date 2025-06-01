package viet.sn.regret.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import viet.sn.regret.entity.profile.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    String profileId;
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;
    Status status;
}
