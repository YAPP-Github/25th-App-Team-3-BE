package org.yapp.domain.term.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yapp.domain.term.TermAgreement;

import java.util.List;

@Repository
public interface TermAgreementRepository extends JpaRepository<TermAgreement, Long> {
    List<TermAgreement> findByUserId(Long userId);
}
