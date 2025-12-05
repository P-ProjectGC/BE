package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plango.admin.application.dto.request.AdminLoginRequest;
import plango.admin.application.dto.response.AdminLoginResponse;
import plango.admin.application.mapper.AdminMapper;
import plango.admin.domain.entity.Admin;
import plango.admin.domain.service.AdminService;
import plango.auth.domain.service.JwtTokenProvider;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class AdminLoginUseCase {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminLoginResponse execute(AdminLoginRequest request) {
        Admin admin = adminService.getByLoginId(request.loginId());

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_ADMIN_PASSWORD);
        }

        // JwtTokenProvider 구현에 맞게 메서드명만 맞춰주면 됨
        String token = jwtTokenProvider.createAdminAccessToken(admin.getId());

        return AdminMapper.toLoginResponse(admin, token);
    }
}
