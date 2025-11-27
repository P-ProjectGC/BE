package plango.room.application.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomCreateResponse(
        Long roomId,
        String roomName,
        String memo,
        LocalDate startDate,
        LocalDate endDate,
        Long ownerId,
        List<Long> memberIds
) {}
