package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.FindPasswordByLoginIdRequest;
import plango.member.domain.service.MemberPasswordResetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckLoginIdForPasswordResetUseCase {

    private final MemberPasswordResetService memberPasswordResetService;

    public void execute(FindPasswordByLoginIdRequest request) {
        memberPasswordResetService.validateLoginId(request.loginId());
    }
}
