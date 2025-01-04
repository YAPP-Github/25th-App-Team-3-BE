package org.yapp.domain.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yapp.domain.auth.application.oauth.service.OauthService;
import org.yapp.domain.auth.presentation.dto.request.OauthLoginRequest;
import org.yapp.domain.auth.presentation.dto.response.OauthLoginResponse;
import org.yapp.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
  private final OauthService oauthService;

  /**
   * 개발중 소셜 로그인으로 사용자의 accessToken을 가져오기 어렵기 때문에 만든 임시 메서드
   */
  @PostMapping("/test/users/{userId}")
  public ResponseEntity<CommonResponse<OauthLoginResponse>> getToken(@PathVariable Long userId) {
    OauthLoginResponse response = oauthService.tmpTokenGet(userId);
    return ResponseEntity.ok(CommonResponse.createSuccess(response));
  }

  @PostMapping("/oauth")
  public ResponseEntity<CommonResponse<OauthLoginResponse>> oauthLogin(@RequestBody OauthLoginRequest request) {
    OauthLoginResponse response = oauthService.login(request);
    return ResponseEntity.ok(CommonResponse.createSuccess(response));
  }
}
