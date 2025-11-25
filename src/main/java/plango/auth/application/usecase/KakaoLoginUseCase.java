package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.domain.service.KakaoAuthService;

@Component
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private final KakaoAuthService kakaoAuthService;

    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        return kakaoAuthService.login(request.getAccessToken());
    }
}
