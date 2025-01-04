package org.yapp.domain.profile.presentation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yapp.domain.auth.presentation.dto.response.OauthLoginResponse;
import org.yapp.domain.profile.Profile;
import org.yapp.domain.profile.application.ProfileImageService;
import org.yapp.domain.profile.application.dto.ProfileCreateDto;
import org.yapp.domain.profile.presentation.request.ProfileUpdateRequest;
import org.yapp.domain.profile.presentation.request.ProfileValueUpdateRequest;
import org.yapp.domain.profile.presentation.response.ProfileResponse;
import org.yapp.domain.profile.application.ProfileService;
import org.yapp.domain.user.User;
import org.yapp.domain.user.application.UserService;
import org.yapp.util.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profileService;
  private final UserService userService;
  private final ProfileImageService profileImageService;

  @PutMapping("/init")
  @Operation(summary = "프로필 초기 등록", description = "현재 로그인된 사용자의 프로필을 초기 등록합니다.", tags = {"Profile"})
  public ResponseEntity<CommonResponse<OauthLoginResponse>> initializeProfile(@AuthenticationPrincipal Long userId,
      @RequestBody @Valid ProfileCreateDto request) {
    //TODO : ProfileCreateDto를 바꿔야한다. 차라리 ProfileUpdateDto랑 합치는게 좋을듯
    Profile profile = profileService.create(request);
    OauthLoginResponse oauthLoginResponse = userService.completeProfileInitialize(profile);
    return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess(oauthLoginResponse));
  }

  @GetMapping
  @Operation(summary = "프로필 조회", description = "현재 로그인된 사용자의 프로필을 조회합니다.", tags = {"Profile"})
  @ApiResponse(responseCode = "200", description = "프로필이 성공적으로 조회되었습니다.")
  public ResponseEntity<CommonResponse<ProfileResponse>> updateProfile(@AuthenticationPrincipal Long userId) {
    User user = userService.getUserById(userId);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(CommonResponse.createSuccess(ProfileResponse.from(user.getProfile())));
  }

  @PutMapping()
  @Operation(summary = "프로필 업데이트", description = "현재 로그인된 사용자의 프로필을 업데이트합니다.", tags = {"Profile"})
  @ApiResponse(responseCode = "200", description = "프로필이 성공적으로 업데이트되었습니다.")
  public ResponseEntity<CommonResponse<ProfileResponse>> updateProfile(@AuthenticationPrincipal Long userId,
      @RequestBody @Valid ProfileUpdateRequest request) {
    Profile profile = profileService.updateByUserId(userId, request);
    return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess(ProfileResponse.from(profile)));
  }

  @PutMapping("/values")
  @Operation(summary = "프로필 가치관 업데이트", description = "현재 로그인된 사용자의 프로필 가치관을 업데이트합니다.", tags = {"ProfileValue"})
  @ApiResponse(responseCode = "200", description = "프로필 가치관이 성공적으로 업데이트되었습니다.")
  public ResponseEntity<CommonResponse<ProfileResponse>> updateProfileValues(
          @AuthenticationPrincipal Long userId,
          @RequestBody @Valid ProfileValueUpdateRequest request
  ){
    Profile profile = profileService.updateProfileValues(userId, request);
    return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess(ProfileResponse.from(profile)));
  }

  @PostMapping("/check-nickname")
  @Operation(summary = "프로필 닉네임 중복 확인", description = "요청 파라미터로 전달된 닉네임이 중복되는지 확인합니다.", tags = {"profile"})
  @ApiResponse(responseCode = "200", description = "중복되지 않은 닉네임입니다.")
  public ResponseEntity<CommonResponse<Boolean>> checkNickname(@RequestParam String nickname) {
    boolean isAvailable = profileService.isNicknameAvailable(nickname);
    return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess(isAvailable));
  }

  @PostMapping("/images")
  @Operation(summary = "프로필 이미지 등록", description = "업로드한 이미지지를 버킷에 등록합니다.", tags = {"profileImage"})
  @ApiResponse(responseCode = "200", description = "이미지가 버킷에 저장되었습니다.")
  public ResponseEntity<CommonResponse<String>> uploadProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
    String profileImageUrl = profileImageService.uploadProfileImage(file);
    return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess(profileImageUrl));
  }
}
