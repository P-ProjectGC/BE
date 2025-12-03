package plango.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FindPasswordByLoginIdRequest(

        @NotBlank(message = "아이디는 필수입니다.")
        String loginId
) {
}
