package org.yapp.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthLoginResponse {
  private String role;
  private String accessToken;
  private String refreshToken;
}
