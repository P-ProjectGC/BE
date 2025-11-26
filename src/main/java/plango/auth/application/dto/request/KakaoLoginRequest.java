package plango.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginRequest {

    /**
     * 카카오 로그인 인가 코드 (authorization_code)
     * 프론트(또는 Swagger)에서 전달받는 값
     */
    @NotBlank(message = "카카오 인가 코드는 필수입니다.")
    private String authorizationCode;
}
