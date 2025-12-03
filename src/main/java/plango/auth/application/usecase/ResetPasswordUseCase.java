package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.ResetPasswordRequest;
import plango.member.domain.service.MemberPasswordResetService;

@Service
@RequiredArgsConstructor
@Transactional
public class ResetPasswordUseCase {

    private final MemberPasswordResetService memberPasswordResetService;

    public void execute(ResetPasswordRequest request) {
        memberPasswordResetService.resetPassword(
                request.loginId(),
                request.newPassword(),
                request.newPasswordConfirm()
        );
    }
}
