package org.yapp.error.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {
  INACTIVE_PROFILE(HttpStatus.FORBIDDEN, "Profile is inactive"),
  NOTFOUND_PROFILE(HttpStatus.NOT_FOUND, "Profile not found"),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}
