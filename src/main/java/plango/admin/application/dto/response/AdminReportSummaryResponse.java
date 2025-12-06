package plango.admin.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

public record AdminReportSummaryResponse(
        Long reportId,
        Long memberId,
        String memberNickname,
        String status,
        LocalDateTime createdAt
) {

    @Builder
    public AdminReportSummaryResponse {
    }
}
