package plango.admin.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.admin.domain.entity.Admin;
import plango.admin.domain.repository.AdminRepository;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin getByLoginId(String loginId) {
        return adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADMIN_NOT_FOUND));
    }
}
