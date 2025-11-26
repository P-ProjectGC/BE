package plango.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오 로그인 요청 DTO
 *  - authorizationCode : 카카오 인가 코드 (authorization_code)
 */
public record KakaoLoginRequest(
        @NotBlank(message = "카카오 인가 코드는 필수입니다.")
        String authorizationCode
) {
}
