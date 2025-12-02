package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.response.DuplicateCheckResponse;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDuplicateCheckUseCase {

    private final MemberService memberService;

    public DuplicateCheckResponse execute(String email) {
        boolean exists = memberService.existsByEmail(email);
        boolean available = !exists;
        return new DuplicateCheckResponse(available, "email", email);
    }
}
