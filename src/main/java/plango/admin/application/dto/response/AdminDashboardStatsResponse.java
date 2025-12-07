package plango.admin.application.dto.response;

import lombok.Builder;

public record AdminDashboardStatsResponse(
        long totalMemberCount,
        long todayNewMemberCount,
        long totalRoomCount,
        long todayNewRoomCount,
        long totalScheduleCount,
        long todayNewScheduleCount,
        long totalNoticeCount,
        long totalInconvenienceReportCount
) {

    @Builder
    public AdminDashboardStatsResponse {
    }
}
