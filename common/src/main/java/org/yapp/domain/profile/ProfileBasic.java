package org.yapp.domain.profile;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProfileBasic {
  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "birthdate")
  private Date birthdate;

  @Column(name = "height")
  private int height;

  @Column(name = "job")
  private String job;

  @Column(name = "location")
  private String location;

  @Column(name = "smoking_status")
  private String smokingStatus;

  @Column(name = "religion")
  private String religion;

  @Column(name = "sns_activity_level")
  private String snsActivityLevel;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "image_url")
  private String imageUrl;
}
