package viet.sn.regret.services;

import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import viet.sn.regret.dto.identity.Credential;
import viet.sn.regret.dto.identity.TokenExchangeParam;
import viet.sn.regret.dto.identity.UserCreationParam;
import viet.sn.regret.dto.request.RegistrationRequest;
import viet.sn.regret.dto.response.ProfileResponse;
import viet.sn.regret.entity.profile.Profile;
import viet.sn.regret.entity.profile.Status;
import viet.sn.regret.exception.AppException;
import viet.sn.regret.exception.ErrorCode;
import viet.sn.regret.exception.ErrorNormalizer;
import viet.sn.regret.mapper.ProfileMapper;
import viet.sn.regret.repository.IdentityClient;
import viet.sn.regret.repository.ProfileRepository;

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
    @PersistenceContext
    final EntityManager entityManager;

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
            profile.setStatus(Status.OFFLINE);
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
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findByUserId(userId).map(profileMapper::toProfileResponse).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
    }
//    public ProfileResponse getMyProfile(String id) {
//        return profileRepository.findById(id).map(profileMapper::toProfileResponse).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
//    }

    public ProfileResponse disconnect() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
        profile.setStatus(Status.OFFLINE);
        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    public List<ProfileResponse> findConnectedUsers() {
        return profileRepository.findAllByStatus(Status.ONLINE).stream()
                .map(profileMapper::toProfileResponse)
                .toList();
    }

    public List<ProfileResponse> findAllEM() {
        return entityManager.createQuery("from Profile ", Profile.class).getResultList().stream().map(profileMapper::toProfileResponse).toList();
    }


    public ProfileResponse connectUser() {
        var viet =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.profile_exist));
        profile.setStatus(Status.ONLINE);
        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }
}


