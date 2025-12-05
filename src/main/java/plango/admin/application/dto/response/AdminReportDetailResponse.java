package plango.admin.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

public record AdminReportDetailResponse(
        Long reportId,
        Long memberId,
        String memberLoginId,
        String memberNickname,
        String content,
        String status,
        String adminMemo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    @Builder
    public AdminReportDetailResponse {
    }
}
