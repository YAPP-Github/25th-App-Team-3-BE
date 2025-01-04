package org.yapp.domain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yapp.application.AuthenticationService;
import org.yapp.domain.auth.application.jwt.JwtUtil;
import org.yapp.domain.auth.presentation.dto.enums.RoleStatus;
import org.yapp.domain.auth.presentation.dto.response.OauthLoginResponse;
import org.yapp.domain.profile.Profile;
import org.yapp.domain.user.User;
import org.yapp.domain.user.dao.UserRepository;
import org.yapp.error.dto.UserErrorCode;
import org.yapp.error.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;
  private final JwtUtil jwtUtil;

  /**
   * Role을 USER로 바꾸고 변경된 토큰을 반환한다.
   *
   * @return 액세스토큰과 리프레시 토큰
   */
  @Transactional
  public OauthLoginResponse completeProfileInitialize(Profile profile) {
    Long userId = authenticationService.getUserId();
    User user =
        userRepository.findById(userId).orElseThrow(() -> new ApplicationException(UserErrorCode.NOTFOUND_USER));
    user.setProfile(profile);
    user.updateUserRole(RoleStatus.USER.getStatus());
    String oauthId = user.getOauthId();
    String accessToken = jwtUtil.createJwt("access_token", userId, oauthId, user.getRole(), 600000L);
    String refreshToken = jwtUtil.createJwt("refresh_token", userId, oauthId, user.getRole(), 864000000L);
    return new OauthLoginResponse(RoleStatus.USER.getStatus(), accessToken, refreshToken);
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new ApplicationException(UserErrorCode.NOTFOUND_USER));
  }

  /**
   * Role을 Register로 바꾸고 변경된 토큰을 반환한다.
   *
   * @return 액세스토큰과 리프레시 토큰
   */
  @Transactional
  public OauthLoginResponse registerPhoneNumber(String phoneNumber) {
    Long userId = authenticationService.getUserId();
    User user =
        userRepository.findById(userId).orElseThrow(() -> new ApplicationException(UserErrorCode.NOTFOUND_USER));
    user.updateUserRole(RoleStatus.REGISTER.getStatus());
    user.initializePhoneNumber(phoneNumber);
    String oauthId = user.getOauthId();
    String accessToken = jwtUtil.createJwt("access_token", userId, oauthId, user.getRole(), 600000L);
    String refreshToken = jwtUtil.createJwt("refresh_token", userId, oauthId, user.getRole(), 864000000L);
    return new OauthLoginResponse(RoleStatus.REGISTER.getStatus(), accessToken, refreshToken);
  }
}
