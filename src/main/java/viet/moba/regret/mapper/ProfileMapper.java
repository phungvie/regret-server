package viet.moba.regret.mapper;

import org.mapstruct.Mapper;
import viet.moba.regret.dto.request.RegistrationRequest;
import viet.moba.regret.dto.response.ProfileResponse;
import viet.moba.regret.entity.profile.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);
    ProfileResponse toProfileResponse(Profile profile);
}
