package org.yapp.domain.profile;

import org.hibernate.annotations.Type;

import java.util.Map;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "value_item")
@Getter
@NoArgsConstructor
public class ValueItem {
  @Id
  @Column(name = "value_item_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String question;

  @Type(JsonType.class)
  @Column(name = "answers", columnDefinition = "longtext", nullable = false)
  private Map<Integer, Object> answers;

  @Builder
  public ValueItem(String question, Map<Integer, Object> answers) {
    this.question = question;
    this.answers = answers;
  }
}
