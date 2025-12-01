package plango.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.application.dto.request.ChangePasswordRequest;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final MemberService memberService;

    @Transactional
    public void execute(Long memberId, ChangePasswordRequest request) {
        memberService.changePassword(
                memberId,
                request.currentPassword(),
                request.newPassword(),
                request.newPasswordConfirm()
        );
    }
}
