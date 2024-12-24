package org.yapp.domain.auth.application.oauth.service;

import org.springframework.stereotype.Service;
import org.yapp.domain.auth.application.jwt.JwtUtil;
import org.yapp.domain.auth.application.oauth.OauthProvider;
import org.yapp.domain.auth.application.oauth.OauthProviderResolver;
import org.yapp.domain.auth.dto.request.OauthLoginRequest;
import org.yapp.domain.auth.dto.response.OauthLoginResponse;
import org.yapp.domain.user.User;
import org.yapp.domain.user.dao.UserRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthService {
  private final OauthProviderResolver oauthProviderResolver;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public OauthLoginResponse login(OauthLoginRequest request) {
    OauthProvider oauthProvider = oauthProviderResolver.find(request.getProviderName());
    String oauthId = request.getProviderName() + oauthProvider.getOAuthProviderUserId(request.getToken());

    //이미 가입된 유저인지 확인하고 가입되어 있지 않으면 회원가입 처리
    Optional<User> userOptional = userRepository.findByOauthId(oauthId);
    if (userOptional.isEmpty()) {
      User newUser = User.builder().oauthId(oauthId).build();
      User savedUser = userRepository.save(newUser);
      Long userId = savedUser.getId();
      String accessToken = jwtUtil.createJwt("access_token", userId, oauthId, "member", 600000L);
      String refreshToken = jwtUtil.createJwt("refresh_token", userId, oauthId, "member", 864000000L);
      return new OauthLoginResponse(true, accessToken, refreshToken);
    }

    //이미 가입한 유저인 경우 로그인 처리
    Long userId = userOptional.get().getId();
    String accessToken = jwtUtil.createJwt("access_token", userId, oauthId, "member", 600000L);
    String refreshToken = jwtUtil.createJwt("refresh_token", userId, oauthId, "member", 864000000L);

    return new OauthLoginResponse(false, accessToken, refreshToken);
  }
}
