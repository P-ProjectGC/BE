package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.domain.service.RefreshTokenService;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutUseCase {

    private final RefreshTokenService refreshTokenService;

    public void execute(Long memberId) {
        refreshTokenService.deleteByMemberId(memberId);
    }
}
