package org.yapp.domain.auth.application.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yapp.global.application.RedisService;
import org.yapp.global.application.SmsSenderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsAuthServiceTest {
  private SmsAuthService smsAuthService;

  @Mock
  private AuthCodeGenerator authCodeGenerator;

  @Mock
  private SmsSenderService smsSenderService;

  @Mock
  private RedisService redisService;

  @BeforeEach
  void setUp() {
    smsAuthService = new SmsAuthService(authCodeGenerator, smsSenderService, redisService);
  }

  @Test
  @DisplayName("인증코드를 SMS 서비스로 전송한다.")
  void shouldSendAuthCodeToPhoneNumber() {
    // Given
    String phoneNumber = "01012345678";
    int mockAuthCode = 123456;
    when(authCodeGenerator.generate()).thenReturn(mockAuthCode);

    // When
    smsAuthService.sendAuthCodeTo(phoneNumber);

    // Then
    ArgumentCaptor<String> phoneCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    verify(smsSenderService, times(1)).sendSMS(phoneCaptor.capture(), messageCaptor.capture());

    assertThat(phoneCaptor.getValue()).isEqualTo(phoneNumber);
    assertThat(messageCaptor.getValue()).isEqualTo("[PIECE] 인증 번호는 123456 입니다.");
  }
}