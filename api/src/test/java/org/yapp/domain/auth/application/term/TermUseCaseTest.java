//package org.yapp.domain.auth.application.term;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.yapp.domain.profile.Profile;
//import org.yapp.domain.term.Term;
//import org.yapp.domain.term.TermAgreement;
//import org.yapp.domain.term.applicatoin.TermUseCase;
//import org.yapp.domain.term.applicatoin.dto.SignupTermsDto;
//import org.yapp.domain.term.dao.TermAgreementRepository;
//import org.yapp.domain.term.dao.TermRepository;
//import org.yapp.domain.user.User;
//import org.yapp.domain.user.application.UserService;
//import org.yapp.error.exception.ApplicationException;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class TermUseCaseTest {
//
//    @Mock
//    private TermRepository termRepository;
//
//    @Mock
//    private TermAgreementRepository termAgreementRepository;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private TermUseCase termUseCase;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void 필수약관_동의_모두_성공() {
//        // given
//        SignupTermsDto dto = new SignupTermsDto(1L, List.of(1L, 2L, 3L));
//        Profile mockProfile = Profile.builder().id(1L).build();
//        User mockUser = User.builder()
//                .oauthId("oauth_123")
//                .name("John Doe")
//                .profile(mockProfile)
//                .role("USER")
//                .build();
//
//
//        when(termRepository.findRequiredActiveTermIds()).thenReturn(List.of(1L, 2L));
//
//        when(termAgreementRepository.findByUserId(1L)).thenReturn(List.of());
//        when(userService.getUserById(1L)).thenReturn(mockUser);
//        when(termRepository.findById(1L)).thenReturn(Optional.of(new Term(1L, "1.0", "이용약관", "내용", true, LocalDateTime.now(), true)));
//        when(termRepository.findById(2L)).thenReturn(Optional.of(new Term(2L, "1.0", "개인정보처리방침", "내용", true, LocalDateTime.now(), true)));
//
//        // when
//        termUseCase.checkTermConstraints(dto);
//
//        // then
//        ArgumentCaptor<List<TermAgreement>> captor = ArgumentCaptor.forClass(List.class);
//        verify(termAgreementRepository).saveAll(captor.capture());
//
//        List<TermAgreement> savedAgreements = captor.getValue();
//        assertThat(savedAgreements).hasSize(2);
//        assertThat(savedAgreements.get(0).getUser().getId()).isEqualTo(1L);
//        assertThat(savedAgreements.get(1).getTerm().getId()).isEqualTo(2L);
//    }
//
//    @Test
//    void 필수약관_미동의시_예외발생() {
//        // given
//        SignupTermsDto dto = new SignupTermsDto(1L, List.of(1L));  // 1번 약관만 동의
//        when(termRepository.findRequiredActiveTermIds()).thenReturn(List.of(1L, 2L));  // 1, 2번 필수 약관 필요
//
//        // when & then
//        assertThatThrownBy(() -> termUseCase.checkTermConstraints(dto))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("모든 필수 약관에 동의해야 회원가입이 가능합니다.");
//    }
//
//    @Test
//    void 존재하지_않는_약관에_동의시_예외발생() {
//        // given
//        SignupTermsDto dto = new SignupTermsDto(1L, List.of(1L, 2L));
//        Profile mockProfile = Profile.builder().id(1L).build();
//        User mockUser = User.builder()
//                .oauthId("oauth_123")
//                .name("John Doe")
//                .profile(mockProfile)
//                .role("USER")
//                .build();
//
//        when(termRepository.findRequiredActiveTermIds()).thenReturn(List.of(1L, 2L));
//        when(termAgreementRepository.findByUserId(1L)).thenReturn(List.of());
//        when(userService.getUserById(1L)).thenReturn(mockUser);
//        when(termRepository.findById(1L)).thenReturn(Optional.of(new Term(1L, "1.0", "이용약관", "내용", true, LocalDateTime.now(), true)));
//        when(termRepository.findById(2L)).thenReturn(Optional.empty());  // 2번 약관은 없음
//
//        // when & then
//        assertThatThrownBy(() -> termUseCase.checkTermConstraints(dto))
//                .isInstanceOf(ApplicationException.class)
//                .hasMessageContaining("유효하지 않은 약관입니다.");
//    }
//}
