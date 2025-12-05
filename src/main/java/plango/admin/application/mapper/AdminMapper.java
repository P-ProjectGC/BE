package plango.admin.application.mapper;

import plango.admin.application.dto.response.AdminLoginResponse;
import plango.admin.domain.entity.Admin;

public class AdminMapper {

    private AdminMapper() {
    }

    public static AdminLoginResponse toLoginResponse(Admin admin, String accessToken) {
        return AdminLoginResponse.builder()
                .adminId(admin.getId())
                .name(admin.getName())
                .accessToken(accessToken)
                .build();
    }
}
