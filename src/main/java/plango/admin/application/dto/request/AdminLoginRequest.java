package plango.admin.application.dto.request;

import lombok.Builder;

public record AdminLoginRequest(
        String loginId,
        String password
) {

    @Builder
    public AdminLoginRequest {
    }
}
