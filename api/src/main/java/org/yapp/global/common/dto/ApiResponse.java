package org.yapp.global.common.dto;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
  private static final String SUCCESS_STATUS = "success";
  private static final String FAIL_STATUS = "fail";
  private static final String ERROR_STATUS = "error";

  private String status;
  private String message;
  private T data;

  private ApiResponse(String status, T data, String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static ApiResponse<?> createError(String message) {
    return new ApiResponse<>(ERROR_STATUS, null, message);
  }

  public static ApiResponse<?> createFail(BindingResult bindingResult) {
    Map<String, String> errors = new HashMap<>();

    List<ObjectError> allErrors = bindingResult.getAllErrors();
    for (ObjectError error : allErrors) {
      if (error instanceof FieldError) {
        errors.put(((FieldError) error).getField(), error.getDefaultMessage());
      } else {
        errors.put(error.getObjectName(), error.getDefaultMessage());
      }
    }
    return new ApiResponse<>(FAIL_STATUS, errors, null);
  }

  public static <T> ApiResponse<T> createSuccess(T data) {
    return new ApiResponse<>(SUCCESS_STATUS, data, null);
  }

  public static ApiResponse<?> createSuccessWithNoContent() {
    return new ApiResponse<>(SUCCESS_STATUS, null, null);
  }
}
