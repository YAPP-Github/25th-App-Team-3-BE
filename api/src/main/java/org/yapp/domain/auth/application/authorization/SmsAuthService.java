package org.yapp.domain.auth.application.authorization;

import org.springframework.stereotype.Service;
import org.yapp.error.code.auth.SmsAuthErrorCode;
import org.yapp.error.exception.ApplicationException;
import org.yapp.global.application.RedisService;
import org.yapp.global.application.SmsSenderService;

import lombok.RequiredArgsConstructor;

/**
 * SMS 인증 서비스
 */
@Service
@RequiredArgsConstructor
public class SmsAuthService {
  private static final long AUTH_CODE_EXPIRE_TIME = 300000;
  private static final String AUTH_CODE_KEY_PREFIX = "authcode:";
  private static final String AUTH_CODE_FORMAT = "[PIECE] 인증 번호는 %06d 입니다.";
  private final AuthCodeGenerator authCodeGenerator;
  private final SmsSenderService smsSenderService;
  private final RedisService redisService;

  /**
   * 인증번호 전송
   *
   * @param phoneNumber 인증 번호를 받을 핸드폰 번호
   */
  public void sendAuthCodeTo(String phoneNumber) {
    int authCode = authCodeGenerator.generate();
    redisService.setKeyWithExpiration(AUTH_CODE_KEY_PREFIX + phoneNumber, String.valueOf(authCode),
        AUTH_CODE_EXPIRE_TIME);
    String authCodeMessage = String.format(AUTH_CODE_FORMAT, authCode);
    smsSenderService.sendSMS(phoneNumber, authCodeMessage);
  }

  /**
   * 인증번호 인증
   *
   * @param phoneNumber 핸드폰번호
   * @param code        인증 번호
   * @return 인증번호 일치 여부
   */
  public void verifySmsAuthCode(String phoneNumber, String code) {
    String expectedCode = redisService.getValue(AUTH_CODE_KEY_PREFIX + phoneNumber);
    if (expectedCode == null) {
      throw new ApplicationException(SmsAuthErrorCode.CODE_NOT_EXIST);
    }
    if (!expectedCode.equals(code)) {
      throw new ApplicationException(SmsAuthErrorCode.CODE_NOT_CORRECT);
    }
  }
}
