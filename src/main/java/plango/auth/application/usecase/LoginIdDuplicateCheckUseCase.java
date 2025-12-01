package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.response.DuplicateCheckResponse;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginIdDuplicateCheckUseCase {

    private final MemberService memberService;

    public DuplicateCheckResponse execute(String loginId) {
        boolean exists = memberService.existsByLoginId(loginId);
        boolean available = !exists;
        return new DuplicateCheckResponse(available, "loginId", loginId);
    }
}
