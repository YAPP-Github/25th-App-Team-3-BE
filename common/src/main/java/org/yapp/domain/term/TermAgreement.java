package org.yapp.domain.term;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yapp.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Column(nullable = false)
    private LocalDateTime agreedAt;
}
