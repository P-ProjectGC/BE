package plango.admin.application.dto.response;

import lombok.Builder;

public record AdminLoginResponse(
        Long adminId,
        String name,
        String accessToken
) {

    @Builder
    public AdminLoginResponse {
    }
}
