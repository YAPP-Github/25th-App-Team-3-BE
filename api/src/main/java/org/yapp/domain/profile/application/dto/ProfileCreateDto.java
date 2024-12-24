package org.yapp.domain.profile.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ProfileCreateDto(@NotBlank String name, @NotBlank String nickName,
                               @NotBlank @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}") String phoneNumber) {
}
