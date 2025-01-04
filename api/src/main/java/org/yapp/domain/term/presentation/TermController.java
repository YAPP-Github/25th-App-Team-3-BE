package org.yapp.domain.term.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yapp.domain.term.Term;
import org.yapp.domain.term.applicatoin.TermService;
import org.yapp.domain.term.presentation.dto.response.TermResponses;
import org.yapp.util.CommonResponse;

import java.util.List;

@RequestMapping("/api/terms")
@Controller
@RequiredArgsConstructor
public class TermController {
    private final TermService termService;

    @GetMapping()
    @Operation(summary = "약관 리스트 조회", description = "서비스에 등록된 모든 약관을 조회합니다.", tags = {"Term"})
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약관 리스트가 성공적으로 조회되었습니다.")
    public ResponseEntity<CommonResponse<TermResponses>> getAllTerms() {
        List<Term> allActiveTerms = termService.getAllActiveTerms();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.createSuccess(TermResponses.from(allActiveTerms)));
    }
}
