package plango.admin.application.dto.response;

public record AdminMonitoringStatusResponse(
        long totalMemberCount,
        long totalRoomCount,
        long totalTripCount,
        long totalFriendRequestCount,
        long totalReportCount,
        long waitingReportCount,
        long processingReportCount,
        long completedReportCount,
        long totalNoticeCount
) {}

