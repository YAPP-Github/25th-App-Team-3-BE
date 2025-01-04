package org.yapp.domain.term.applicatoin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yapp.domain.term.Term;
import org.yapp.domain.term.dao.TermRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    @Transactional(readOnly = true)
    public List<Term> getAllActiveTerms() {
        return  termRepository.findAllByIsActiveTrue();
    }
}
