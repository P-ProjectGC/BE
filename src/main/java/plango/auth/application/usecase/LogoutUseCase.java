package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutUseCase {

    public void execute() {
        // 현재는 서버에서 별도의 세션이나 토큰을 저장하지 않습니다.
        // 추후 리프레시 토큰, 세션 등을 도입하면
        // 이 메서드에서 무효화 로직을 추가합니다.
    }
}
