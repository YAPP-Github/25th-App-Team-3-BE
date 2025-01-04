package org.yapp.domain.term;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private long id;

    private String version;

    private String title;

    private String content;

    private boolean required;

    @Column(nullable = false)

    private LocalDateTime startDate;
    @Column(nullable = false)

    @ColumnDefault(value = "true")
    private boolean isActive;
}
