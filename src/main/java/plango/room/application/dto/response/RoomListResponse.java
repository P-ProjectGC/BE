package plango.room.application.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RoomListResponse(
        Long roomId,
        String roomName,
        String memo,
        LocalDate startDate,
        LocalDate endDate,
        boolean host
) {}
