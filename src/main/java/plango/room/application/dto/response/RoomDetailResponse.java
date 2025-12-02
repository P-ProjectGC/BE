package plango.room.application.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record RoomDetailResponse(
        Long roomId,
        String roomName,
        String memo,
        LocalDate startDate,
        LocalDate endDate,
        boolean host,
        List<RoomMemberResponse> members
) {

    @Builder
    public RoomDetailResponse {
    }
}
