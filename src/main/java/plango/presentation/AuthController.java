package plango.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.application.dto.request.KakaoLoginRequest;
import plango.application.dto.response.LoginResponse;
import plango.application.usecase.KakaoLoginUseCase;
import plango.global.common.response.CommonResponse;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;

    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰으로 로그인합니다.")
    @PostMapping("/login/kakao")
    public CommonResponse<LoginResponse> loginWithKakao(
            @Valid @RequestBody KakaoLoginRequest request
    ) {
        LoginResponse response = kakaoLoginUseCase.execute(request);

        return CommonResponse.createSuccess("카카오 로그인에 성공했습니다.", response);
    }
}
