package plango.room.application.dto.response;

import lombok.Builder;

@Builder
public record ScheduleCreateResponse(
        Long scheduleId,
        int dayIndex,
        String startTime,
        String endTime,
        Long roomPlaceId,
        String memo,
        String placeName,
        String address,
        Double lat,
        Double lng
) { }
