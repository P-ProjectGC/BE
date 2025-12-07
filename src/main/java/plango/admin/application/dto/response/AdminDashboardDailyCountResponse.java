package plango.admin.application.dto.response;

import java.time.LocalDate;

public record AdminDashboardDailyCountResponse(
        LocalDate date,
        long newMemberCount
) {}

