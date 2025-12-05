package plango.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.request.MemberLoginRequest;
import plango.auth.application.dto.request.MemberSignUpRequest;
import plango.auth.application.dto.request.FindLoginIdRequest;
import plango.auth.application.dto.request.VerifyLoginIdCodeRequest;
import plango.auth.application.dto.request.FindPasswordByLoginIdRequest;
import plango.auth.application.dto.request.ResetPasswordRequest;
import plango.auth.application.dto.request.TokenReissueRequest;
import plango.auth.application.dto.response.DuplicateCheckResponse;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.MemberSignUpResponse;
import plango.auth.application.dto.response.FindLoginIdPreviewResponse;
import plango.auth.application.dto.response.FindLoginIdResultResponse;
import plango.auth.application.dto.response.SendLoginIdVerificationCodeResponse;
import plango.auth.application.usecase.EmailDuplicateCheckUseCase;
import plango.auth.application.usecase.KakaoLoginUseCase;
import plango.auth.application.usecase.LoginIdDuplicateCheckUseCase;
import plango.auth.application.usecase.LogoutUseCase;
import plango.auth.application.usecase.MemberLoginUseCase;
import plango.auth.application.usecase.MemberSignUpUseCase;
import plango.auth.application.usecase.NicknameDuplicateCheckUseCase;
import plango.auth.application.usecase.FindLoginIdUseCase;
import plango.auth.application.usecase.SendLoginIdVerificationCodeUseCase;
import plango.auth.application.usecase.VerifyLoginIdCodeUseCase;
import plango.auth.application.usecase.CheckLoginIdForPasswordResetUseCase;
import plango.auth.application.usecase.ResetPasswordUseCase;
import plango.auth.application.usecase.TokenReissueUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(name = "Auth API", description = "인증 / 인가 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberSignUpUseCase memberSignUpUseCase;
    private final NicknameDuplicateCheckUseCase nicknameDuplicateCheckUseCase;
    private final LoginIdDuplicateCheckUseCase loginIdDuplicateCheckUseCase;
    private final EmailDuplicateCheckUseCase emailDuplicateCheckUseCase;
    private final MemberLoginUseCase memberLoginUseCase;
    private final KakaoLoginUseCase kakaoLoginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final FindLoginIdUseCase findLoginIdUseCase;
    private final SendLoginIdVerificationCodeUseCase sendLoginIdVerificationCodeUseCase;
    private final VerifyLoginIdCodeUseCase verifyLoginIdCodeUseCase;
    private final CheckLoginIdForPasswordResetUseCase checkLoginIdForPasswordResetUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;

    @Operation(summary = "일반 회원가입", description = "이름, 닉네임, 아이디, 비밀번호, 이메일로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<MemberSignUpResponse>> signUp(
            @Valid
            @RequestBody
            MemberSignUpRequest request
    ) {
        MemberSignUpResponse response = memberSignUpUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.MEMBER_SIGN_UP_SUCCESS, response)
        );
    }

    @Operation(summary = "일반 로그인", description = "아이디와 비밀번호로 로그인합니다.")
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

    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 사용해 로그인합니다.")
    @PostMapping("/login/kakao")
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

    @Operation(summary = "로그아웃", description = "현재 로그인된 사용자를 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
            @AuthenticationPrincipal Long memberId
    ) {
        logoutUseCase.execute(memberId);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS)
        );
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임이 사용 가능한지 확인합니다.")
    @GetMapping("/check/nickname")
    public ResponseEntity<CommonResponse<DuplicateCheckResponse>> checkNickname(
            @RequestParam
            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
            String nickname
    ) {
        DuplicateCheckResponse response = nicknameDuplicateCheckUseCase.execute(nickname);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.MEMBER_NICKNAME_CHECK_SUCCESS, response)
        );
    }

    @Operation(summary = "아이디 중복 확인", description = "아이디가 사용 가능한지 확인합니다.")
    @GetMapping("/check/login-id")
    public ResponseEntity<CommonResponse<DuplicateCheckResponse>> checkLoginId(
            @RequestParam
            @NotBlank(message = "아이디는 필수입니다.")
            @Pattern(
                    regexp = "^[a-z0-9]{4,16}$",
                    message = "아이디는 영문 소문자와 숫자 조합 4~16자여야 합니다."
            )
            String loginId
    ) {
        DuplicateCheckResponse response = loginIdDuplicateCheckUseCase.execute(loginId);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.MEMBER_LOGIN_ID_CHECK_SUCCESS, response)
        );
    }

    @Operation(summary = "이메일 중복 확인", description = "이메일이 사용 가능한지 확인합니다.")
    @GetMapping("/check/email")
    public ResponseEntity<CommonResponse<DuplicateCheckResponse>> checkEmail(
            @RequestParam
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "유효한 이메일 형식이 아닙니다.")
            String email
    ) {
        DuplicateCheckResponse response = emailDuplicateCheckUseCase.execute(email);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.MEMBER_EMAIL_CHECK_SUCCESS, response)
        );
    }

    @Operation(
            summary = "아이디 찾기 (마스킹)",
            description = "회원가입 시 사용한 이메일로 아이디를 조회하고, 뒤 5자리를 마스킹한 아이디를 반환합니다."
    )
    @PostMapping("/find-id")
    public ResponseEntity<CommonResponse<FindLoginIdPreviewResponse>> findLoginId(
            @Valid
            @RequestBody
            FindLoginIdRequest request
    ) {
        FindLoginIdPreviewResponse response = findLoginIdUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(
            summary = "아이디 찾기 추가 인증번호 발송",
            description = "아이디를 찾기 위해 입력한 이메일로 6자리 인증번호를 전송합니다. 테스트 편의를 위해 응답에 인증번호가 함께 포함됩니다."
    )
    @PostMapping("/find-id/send-code")
    public ResponseEntity<CommonResponse<SendLoginIdVerificationCodeResponse>> sendFindIdCode(
            @Valid
            @RequestBody
            FindLoginIdRequest request
    ) {
        SendLoginIdVerificationCodeResponse response =
                sendLoginIdVerificationCodeUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(
            summary = "아이디 찾기 인증번호 검증",
            description = "이메일로 발송된 6자리 인증번호를 검증하고, 성공 시 전체 아이디를 반환합니다."
    )
    @PostMapping("/find-id/verify-code")
    public ResponseEntity<CommonResponse<FindLoginIdResultResponse>> verifyFindIdCode(
            @Valid
            @RequestBody
            VerifyLoginIdCodeRequest request
    ) {
        FindLoginIdResultResponse response =
                verifyLoginIdCodeUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(
            summary = "비밀번호 찾기 - 아이디 확인",
            description = "입력한 아이디가 일반 회원가입 사용자이면서 존재하는지 확인합니다."
    )
    @PostMapping("/find-password/check-login-id")
    public ResponseEntity<CommonResponse<Void>> checkLoginIdForPasswordReset(
            @Valid
            @RequestBody
            FindPasswordByLoginIdRequest request
    ) {
        checkLoginIdForPasswordResetUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.SUCCESS)
        );
    }

    @Operation(
            summary = "비밀번호 찾기 - 새 비밀번호 설정",
            description = "아이디를 통해 새 비밀번호를 설정합니다. 카카오 로그인 회원은 사용할 수 없습니다."
    )
    @PostMapping("/find-password/reset")
    public ResponseEntity<CommonResponse<Void>> resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest request
    ) {
        resetPasswordUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(ResponseMessage.MEMBER_PASSWORD_CHANGE_SUCCESS)
        );
    }

    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 사용해 액세스 토큰과 리프레시 토큰을 재발급합니다."
    )
    @PostMapping("/token/reissue")
    public ResponseEntity<CommonResponse<KakaoLoginResponse>> reissueToken(
            @Valid
            @RequestBody
            TokenReissueRequest request
    ) {
        KakaoLoginResponse response = tokenReissueUseCase.execute(request);

        return ResponseEntity.ok(
                CommonResponse.success(
                        ResponseMessage.AUTH_TOKEN_REISSUE_SUCCESS,
                        response
                )
        );
    }
}
