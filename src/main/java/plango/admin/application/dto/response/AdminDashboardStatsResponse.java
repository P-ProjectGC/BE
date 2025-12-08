package plango.admin.application.dto.response;

import lombok.Builder;
import java.util.Map;
import java.util.List;

public record AdminDashboardStatsResponse(
        long totalMemberCount,
        long todayNewMemberCount,
        long totalRoomCount,
        long todayNewRoomCount,
        long totalScheduleCount,
        long todayNewScheduleCount,
        long totalNoticeCount,
        long totalInconvenienceReportCount,
        Map<String, Long> loginMethodStats,
        List<MonthlyRoomStatResponse> monthlyRoomStats
) {

    @Builder
    public AdminDashboardStatsResponse {
    }
}
