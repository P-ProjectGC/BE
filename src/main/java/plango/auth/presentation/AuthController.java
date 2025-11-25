package plango.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.usecase.KakaoLoginUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;

    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰으로 로그인합니다.")
    @PostMapping("/kakao")
    public CommonResponse<KakaoLoginResponse> kakaoLogin(
            @Valid @RequestBody KakaoLoginRequest request
    ) {
        KakaoLoginResponse response = kakaoLoginUseCase.execute(request);
        return CommonResponse.createSuccess(
                ResponseMessage.KAKAO_LOGIN_SUCCESS.getMessage(),
                response
        );
    }
}
