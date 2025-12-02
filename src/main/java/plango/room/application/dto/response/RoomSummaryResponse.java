package plango.room.application.dto.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RoomSummaryResponse(
        Long roomId,
        String roomName,
        String memo,
        LocalDate startDate,
        LocalDate endDate,
        boolean host // 내가 방장인지 여부
) {}
