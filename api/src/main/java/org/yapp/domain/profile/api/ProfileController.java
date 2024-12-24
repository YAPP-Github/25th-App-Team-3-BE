package org.yapp.domain.profile.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yapp.domain.profile.Profile;
import org.yapp.domain.profile.api.request.ProfileUpdateRequest;
import org.yapp.domain.profile.api.response.ProfileResponse;
import org.yapp.domain.profile.application.ProfileService;
import org.yapp.domain.user.User;
import org.yapp.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profileService;

  @GetMapping
  @Operation(summary = "프로필 조회", description = "현재 로그인된 사용자의 프로필을 조회합니다.", tags = {"Profile"})
  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필이 성공적으로 조회되었습니다.")
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@AuthenticationPrincipal User user) {
    Profile profile = profileService.getProfileById(user.getProfile().getId());
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(ProfileResponse.from(profile)));
  }

  @PutMapping()
  @Operation(summary = "프로필 업데이트", description = "현재 로그인된 사용자의 프로필을 업데이트합니다.", tags = {"Profile"})
  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필이 성공적으로 업데이트되었습니다.")
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@AuthenticationPrincipal User user,
      @RequestBody @Valid ProfileUpdateRequest request) {
    Profile profile = profileService.updateByUserId(user.getId(), request);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(ProfileResponse.from(profile)));
  }
}
