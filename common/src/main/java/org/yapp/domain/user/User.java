package org.yapp.domain.user;

import org.yapp.domain.profile.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_common")
@Entity
@Getter
@NoArgsConstructor
public class User {
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "oauth_id")
  private String oauthId;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phoneNumber;

  @Column(name = "role")
  private String role = "USER";

  @OneToOne
  @JoinColumn(name = "profile_id", unique = true)  // User가 profile_id를 FK로 가짐
  private Profile profile;

  @Builder
  public User(String oauthId, String name, Profile profile, String role) {
    this.oauthId = oauthId;
    this.name = name;
    this.profile = profile;
    this.role = role;
  }

  public void initializePhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public void updateUserRole(String role) {
    this.role = role;
  }
}

