package plango.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plango.application.dto.request.KakaoLoginRequest;
import plango.application.dto.response.KakaoLoginResponse;
import plango.domain.service.KakaoAuthService;

@Component
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private final KakaoAuthService kakaoAuthService;

    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        return kakaoAuthService.login(request.getAccessToken());
    }
}
