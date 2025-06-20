package viet.sn.regret.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import viet.sn.regret.dto.ApiResponse;
import viet.sn.regret.dto.request.RegistrationRequest;
import viet.sn.regret.dto.response.ProfileResponse;
import viet.sn.regret.services.ProfileService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileController {
    final ProfileService profileService;

    @PostMapping("/register")
    @ResponseBody
    public ApiResponse<ProfileResponse> register(@RequestBody RegistrationRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.register(request))
                .build();
    }

    @GetMapping("/profiles")
    @ResponseBody
    public ApiResponse<List<ProfileResponse>> getAllProfile(){
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfile())
                .build();
    }
//    @GetMapping("/id/{id}")
//    @ResponseBody
//    public ApiResponse<ProfileResponse> getProfileById(@PathVariable String id){
//        return ApiResponse.<ProfileResponse>builder()
//                .result(profileService.getMyProfile(id))
//                .build();
//    }
    @GetMapping("/my-profile")
    @ResponseBody
    public ApiResponse<ProfileResponse> getMyProfiles() {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getMyProfile())
                .build();
    }
    @GetMapping("/all-em")
    @ResponseBody
    public ApiResponse<List<ProfileResponse>> getAllEM() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.findAllEM())
                .build();
    }


    //websocket methods
    @PostMapping("/disconnectUser")
    @ResponseBody
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public ProfileResponse disconnectUser(Principal principal) {
        return profileService.disconnect(principal.getName());
    }

    @PostMapping("/connectUser")
    @ResponseBody
    @MessageMapping("/user.connectUser")
    @SendTo("/user/public")
    public ProfileResponse connectUser(Principal principal) {
        return profileService.connectUser(principal.getName());
    }

    @GetMapping("/connect-users")
    @ResponseBody
    public ApiResponse<List<ProfileResponse>> findConnectedUsers() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.findConnectedUsers())
                .build();
    }


}
