package plango.admin.application.dto.response;

import lombok.Builder;

public record MonthlyRoomStatResponse(
        String yearMonth,   // 예: "2024-05"
        long totalRoomCount
) {
    @Builder
    public MonthlyRoomStatResponse {
    }
}
