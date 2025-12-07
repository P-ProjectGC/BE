package plango.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.admin.application.dto.response.AdminDashboardStatsResponse;
import plango.admin.application.usecase.AdminDashboardStatsUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(
        name = "관리자 대시보드",
        description = "관리자 모니터링용 통계 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardStatsUseCase adminDashboardStatsUseCase;

    @Operation(
            summary = "대시보드 통계 조회",
            description = "회원 수, 여행방 수, 일정 수, 공지사항 수, 신고 건수 등을 통합 조회합니다."
    )
    @GetMapping
    public CommonResponse<AdminDashboardStatsResponse> getStats() {
        AdminDashboardStatsResponse response = adminDashboardStatsUseCase.execute();

        return CommonResponse.success(
                ResponseMessage.ADMIN_DASHBOARD_STATS_SUCCESS,
                response
        );
    }
}
