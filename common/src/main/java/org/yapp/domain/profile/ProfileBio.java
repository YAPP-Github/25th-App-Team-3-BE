package org.yapp.domain.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileBio {
  @Column(name = "introduction", length = 500)
  private String introduction;

  @Column(name = "goal", length = 500)
  private String goal;

  @Column(name = "interest", length = 500)
  private String interest;
}