package plango.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plango.admin.application.dto.request.AdminReportUpdateStatusRequest;
import plango.admin.application.dto.response.AdminReportDetailResponse;
import plango.admin.application.dto.response.AdminReportSummaryResponse;
import plango.admin.application.usecase.AdminGetReportDetailUseCase;
import plango.admin.application.usecase.AdminGetReportListUseCase;
import plango.admin.application.usecase.AdminUpdateReportStatusUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

import java.util.List;

@Tag(
        name = "관리자 신고관리",
        description = "사용자의 불편 신고 목록 조회/상세/상태 변경 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final AdminGetReportListUseCase adminGetReportListUseCase;

    private final AdminGetReportDetailUseCase adminGetReportDetailUseCase;

    private final AdminUpdateReportStatusUseCase adminUpdateReportStatusUseCase;

    @Operation(
            summary = "신고 목록 조회",
            description = "대기/처리중/완료 상태를 포함하여 전체 신고 목록을 조회합니다."
    )
    @GetMapping
    public CommonResponse<List<AdminReportSummaryResponse>> getReports() {
        List<AdminReportSummaryResponse> responses = adminGetReportListUseCase.execute();

        return CommonResponse.success(
                ResponseMessage.ADMIN_REPORT_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "신고 상세 조회",
            description = "신고 ID(reportId)를 기준으로 상세 정보를 조회합니다."
    )
    @GetMapping("/{reportId}")
    public CommonResponse<AdminReportDetailResponse> getReportDetail(
            @PathVariable Long reportId
    ) {
        AdminReportDetailResponse response = adminGetReportDetailUseCase.execute(reportId);

        return CommonResponse.success(
                ResponseMessage.ADMIN_REPORT_DETAIL_GET_SUCCESS,
                response
        );
    }

    @Operation(
            summary = "신고 상태 변경",
            description = "신고의 상태(WAITING / PROCESSING / COMPLETED)와 관리자 메모를 수정합니다."
    )
    @PatchMapping("/{reportId}/status")
    public CommonResponse<Void> updateReportStatus(
            @PathVariable Long reportId,
            @RequestBody AdminReportUpdateStatusRequest request
    ) {
        adminUpdateReportStatusUseCase.execute(reportId, request);

        return CommonResponse.success(
                ResponseMessage.ADMIN_REPORT_STATUS_UPDATE_SUCCESS
        );
    }
}
