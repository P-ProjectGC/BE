package plango.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오 로그인 요청 DTO
 * Android 카카오 SDK에서 발급받은 accessToken, idToken을 전달합니다.
 * idToken은 선택값이며 현재 서버에서는 accessToken을 기반으로 카카오 사용자 정보를 조회합니다.
 */
public record KakaoLoginRequest(
        @NotBlank(message = "카카오 access token은 필수입니다.")
        String accessToken,
        String idToken
) {
}

