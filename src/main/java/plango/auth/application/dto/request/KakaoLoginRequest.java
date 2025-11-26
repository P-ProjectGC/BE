package plango.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오 로그인 인가 코드 요청 DTO
 */
public record KakaoLoginRequest(
        @NotBlank(message = "카카오 인가 코드는 필수입니다.")
        String authorizationCode
) {
}
