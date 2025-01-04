package org.yapp.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SmsAuthRequest {
  private String phoneNumber;
}
