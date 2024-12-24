package org.yapp.domain.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_value")
@Getter
@NoArgsConstructor
public class ProfileValue {
  @Id
  @Column(name = "profile_value_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "value_item_id", nullable = false)
  private ValueItem valueItem;

  @Column(nullable = false)
  private Integer selectedAnswer;

  @Builder
  public ProfileValue(Profile profile, ValueItem valueItem, Integer selectedAnswer) {
    this.profile = profile;
    this.valueItem = valueItem;
    this.selectedAnswer = selectedAnswer;
  }
}
