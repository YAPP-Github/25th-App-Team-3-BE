package org.yapp.domain.profile;

import org.yapp.domain.user.User;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profile")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
  @Id
  @Column(name = "profile_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "profile")
  private User user;

  @Embedded
  private ProfileBasic profileBasic;

  @Embedded
  private ProfileBio profileBio;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProfileValue> profileValues;

  public void updateBio(ProfileBio profileBio) {
    this.profileBio = profileBio;
  }

  public void updateBasic(ProfileBasic profileBasic) {
    this.profileBasic = profileBasic;
  }
}
