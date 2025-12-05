package plango.admin.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plango.admin.application.dto.request.AdminLoginRequest;
import plango.admin.application.dto.response.AdminLoginResponse;
import plango.admin.application.usecase.AdminLoginUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminLoginUseCase adminLoginUseCase;

    @PostMapping("/login")
    public CommonResponse<AdminLoginResponse> login(
            @RequestBody AdminLoginRequest request
    ) {
        AdminLoginResponse response = adminLoginUseCase.execute(request);

        return CommonResponse.success(
                ResponseMessage.SUCCESS,
                response
        );
    }
}
