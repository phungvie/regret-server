package viet.moba.regret.services;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import viet.moba.regret.dto.identity.Credential;
import viet.moba.regret.dto.identity.TokenExchangeParam;
import viet.moba.regret.dto.identity.UserCreationParam;
import viet.moba.regret.dto.request.RegistrationRequest;
import viet.moba.regret.dto.response.ProfileResponse;
import viet.moba.regret.exception.AppException;
import viet.moba.regret.exception.ErrorCode;
import viet.moba.regret.exception.ErrorNormalizer;
import viet.moba.regret.mapper.ProfileMapper;
import viet.moba.regret.repository.IdentityClient;
import viet.moba.regret.repository.ProfileRepository;

import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    final ProfileRepository profileRepository;
    final IdentityClient identityClient;
    @Value("${idp.client-id}")
    private String CLIENT_ID;
    @Value("${idp.client-secret}")
    private String CLIENT_SECRET;
    final ProfileMapper profileMapper;

    final ErrorNormalizer errorNormalizer;

    public ProfileResponse register(RegistrationRequest request) {
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(CLIENT_ID)
                    .client_secret(CLIENT_SECRET)
                    .scope("openid")
                    .build());

            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .temporary(false)
                                    .value(request.getPassword())
                                    .build()))
                            .build());

            String userId = extractUserId(creationResponse);

            var profile = profileMapper.toProfile(request);
            profile.setUserId(userId);

            profile = profileRepository.save(profile);

            return profileMapper.toProfileResponse(profile);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }

    private String extractUserId(ResponseEntity<?> response) {
        String location = Objects.requireNonNull(response.getHeaders().get("Location")).getFirst();
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

    public List<ProfileResponse> getAllProfile() {
        return profileRepository.findAll().stream().map(profileMapper::toProfileResponse).toList();
    }

    public ProfileResponse getMyProfile() {
        String userId= SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findByUserId(userId).map(profileMapper::toProfileResponse).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
    }
    public ProfileResponse getMyProfile(String id) {
        return profileRepository.findById(id).map(profileMapper::toProfileResponse).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
    }

}


