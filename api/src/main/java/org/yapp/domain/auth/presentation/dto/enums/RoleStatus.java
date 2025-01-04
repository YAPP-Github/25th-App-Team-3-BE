package org.yapp.domain.auth.presentation.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleStatus {
  NONE("NONE"),
  REGISTER("REGISTER"),
  USER("USER");
  private String status;

  @JsonValue
  public String getStatus() {
    return status;
  }
}
