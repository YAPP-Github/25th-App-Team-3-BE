package org.yapp.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TermErrorCode implements ErrorCode {
    INACTIVE_TERM(HttpStatus.FORBIDDEN, "접근이 금지된 약관입니다."),
    NOTFOUND_TERM(HttpStatus.NOT_FOUND, "유효하지 않은 약관입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
