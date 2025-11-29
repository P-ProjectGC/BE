package plango.room.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ScheduleCreateRequest(

        @NotNull
        Long roomPlaceId,

        @NotNull
        Integer dayIndex,

        @NotNull
        String startTime,   // "10:00" 형태

        @NotNull
        String endTime,

        String memo

) { }
