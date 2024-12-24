package org.yapp.error.exception;

import org.yapp.error.dto.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {
  private final ErrorCode errorCode;
}
