package org.yapp.domain.term.presentation.dto.response;

import org.yapp.domain.term.Term;

import java.util.List;

public record TermResponses (List<TermResponse> responses) {
    public static TermResponses from(List<Term> terms) {
        List<TermResponse> list = terms.stream().map(TermResponse::from).toList();
        return new TermResponses(list);
    }
}
