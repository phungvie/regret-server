package viet.sn.regret.mapper;

import org.mapstruct.Mapper;
import viet.sn.regret.dto.request.RegistrationRequest;
import viet.sn.regret.dto.response.ProfileResponse;
import viet.sn.regret.entity.profile.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);
    ProfileResponse toProfileResponse(Profile profile);
}
