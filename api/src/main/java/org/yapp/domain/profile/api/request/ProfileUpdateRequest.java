package org.yapp.domain.profile.api.request;

import org.yapp.domain.profile.ProfileBasic;
import org.yapp.domain.profile.ProfileBio;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileUpdateRequest(@NotBlank(message = "닉네임은 비어있을 수 없습니다.") String nickname,

                                   @NotBlank(message = "생일은 비어있을 수 없습니다.") String birthdate,

                                   @Min(value = 50, message = "키는 최소 50cm 이상이어야 합니다.") @Max(value = 300,
                                       message = "키는 250cm를 초과할 수 없습니다.") int height,

                                   @NotBlank(message = "직업은 비어있을 수 없습니다.") String job,

                                   @NotBlank(message = "위치는 비어있을 수 없습니다.") String location,

                                   String smokingStatus, String religion, String snsActivityLevel,

                                   @Pattern(regexp = "^\\d{10,11}$",
                                       message = "전화번호는 10자리에서 11자리 숫자여야 합니다.") String phoneNumber,

                                   String imageUrl,

                                   @Size(max = 500, message = "자기소개는 500자를 초과할 수 없습니다.") String introduction,

                                   @Size(max = 500, message = "목표는 500자를 초과할 수 없습니다.") String goal,

                                   @Size(max = 500, message = "관심사는 500자를 초과할 수 없습니다.") String interest) {

  public ProfileBio toProfileBio() {
    return ProfileBio.builder().introduction(introduction).goal(goal).interest(interest).build();
  }

  public ProfileBasic toProfileBasic() {
    return ProfileBasic.builder()
                       .nickname(nickname)
                       .birthdate(java.sql.Date.valueOf(birthdate))
                       .height(height)
                       .job(job)
                       .location(location)
                       .smokingStatus(smokingStatus)
                       .religion(religion)
                       .snsActivityLevel(snsActivityLevel)
                       .phoneNumber(phoneNumber)
                       .imageUrl(imageUrl)
                       .build();
  }

}