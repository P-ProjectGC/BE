package plango.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.request.MemberLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.usecase.KakaoLoginUseCase;
import plango.auth.application.usecase.LogoutUseCase;
import plango.auth.application.usecase.MemberLoginUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;
    private final MemberLoginUseCase memberLoginUseCase;
    private final LogoutUseCase logoutUseCase;

    @Operation(summary = "일반 로그인", description = "로그인 ID와 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<KakaoLoginResponse>> login(
            @Valid
            @RequestBody
            MemberLoginRequest request
    ) {
        KakaoLoginResponse response = memberLoginUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 사용하여 간편 로그인을 수행합니다.")
    @PostMapping("/kakao/login")
    public ResponseEntity<CommonResponse<KakaoLoginResponse>> kakaoLogin(
            @Valid
            @RequestBody
            KakaoLoginRequest request
    ) {
        KakaoLoginResponse response = kakaoLoginUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "로그아웃", description = "로그인 정보를 삭제하고 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout() {
        logoutUseCase.execute();

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, null)
        );
    }
}
