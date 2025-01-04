package org.yapp.domain.term.applicatoin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yapp.domain.term.TermAgreement;
import org.yapp.domain.term.applicatoin.dto.SignupTermsDto;
import org.yapp.domain.term.dao.TermAgreementRepository;
import org.yapp.domain.term.dao.TermRepository;
import org.yapp.domain.user.User;
import org.yapp.domain.user.application.UserService;
import org.yapp.error.dto.TermErrorCode;
import org.yapp.error.exception.ApplicationException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TermUseCase {
    private final TermRepository termRepository;
    private final TermAgreementRepository termAgreementRepository;
    private final UserService userService;

    @Transactional
    public void checkTermConstraints(SignupTermsDto dto) {
        List<Long> requiredTermIds = termRepository.findRequiredActiveTermIds();
        List<Long> agreedTermIds = dto.agreedTermsId();

        boolean hasAgreedToAllRequired = agreedTermIds.containsAll(requiredTermIds);

        if (!hasAgreedToAllRequired) {
            throw new IllegalStateException("모든 필수 약관에 동의해야 회원가입이 가능합니다.");
        }

        User user = userService.getUserById(dto.userId());

        List<Long> alreadyAgreedTermIds = termAgreementRepository.findByUserId(user.getId())
                .stream()
                .map(agreement -> agreement.getTerm().getId())
                .toList();

        List<Long> newAgreementTermIds = agreedTermIds.stream()
                .filter(termId -> !alreadyAgreedTermIds.contains(termId))
                .toList();

        List<TermAgreement> agreements = newAgreementTermIds.stream()
                .map(termId -> TermAgreement.builder()
                        .user(user)
                        .term(termRepository.findById(termId)
                                .orElseThrow(() -> new ApplicationException(TermErrorCode.NOTFOUND_TERM)))
                        .agreedAt(LocalDateTime.now())
                        .build())
                .toList();

        termAgreementRepository.saveAll(agreements);
    }
}
