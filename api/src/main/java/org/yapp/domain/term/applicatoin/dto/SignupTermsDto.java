package org.yapp.domain.term.applicatoin.dto;

import java.util.List;

public record SignupTermsDto (
        Long userId,
        List<Long> agreedTermsId
){
}
