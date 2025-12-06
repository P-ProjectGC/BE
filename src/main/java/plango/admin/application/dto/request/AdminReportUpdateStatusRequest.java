package plango.admin.application.dto.request;

import lombok.Builder;

public record AdminReportUpdateStatusRequest(
        String status,      // "WAITING" / "PROCESSING" / "COMPLETED"
        String adminMemo
) {

    @Builder
    public AdminReportUpdateStatusRequest {
    }
}
