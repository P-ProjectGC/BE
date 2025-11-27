package plango.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.usecase.KakaoLoginUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;

    @PostMapping("/kakao/login")
    public ResponseEntity<CommonResponse<KakaoLoginResponse>> kakaoLogin(
            @RequestBody @Valid KakaoLoginRequest request
    ) {
        KakaoLoginResponse response = kakaoLoginUseCase.execute(request);
        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }
}
