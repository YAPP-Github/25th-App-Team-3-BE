package org.yapp.domain.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yapp.domain.auth.application.authorization.SmsAuthService;
import org.yapp.domain.auth.presentation.dto.request.SmsAuthRequest;
import org.yapp.domain.auth.presentation.dto.request.SmsAuthVerifyRequest;
import org.yapp.domain.auth.presentation.dto.response.OauthLoginResponse;
import org.yapp.domain.user.application.UserService;
import org.yapp.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/register/sms/auth")
public class SmsAuthController {
  private final SmsAuthService smsAuthService;
  private final UserService userService;

  @PostMapping("/code")
  public ResponseEntity<CommonResponse> sendSms(@RequestBody SmsAuthRequest smsAuthRequest) {
    smsAuthService.sendAuthCodeTo(smsAuthRequest.getPhoneNumber());
    return ResponseEntity.ok(CommonResponse.createSuccessWithNoContent());
  }

  @PostMapping("/code/verify")
  public ResponseEntity<CommonResponse> verifyCode(@RequestBody SmsAuthVerifyRequest request) {
    smsAuthService.verifySmsAuthCode(request.getPhoneNumber(), request.getCode());
    OauthLoginResponse registerToken = userService.registerPhoneNumber(request.getPhoneNumber());
    return ResponseEntity.ok(CommonResponse.createSuccess(registerToken));
  }
}
