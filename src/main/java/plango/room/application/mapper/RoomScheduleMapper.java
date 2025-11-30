package plango.room.application.mapper;

import lombok.experimental.UtilityClass;
import plango.room.application.dto.response.ScheduleCreateResponse;
import plango.room.domain.entity.RoomSchedule;

@UtilityClass
public class RoomScheduleMapper {

    public ScheduleCreateResponse toCreateResponse(RoomSchedule schedule) {
        return ScheduleCreateResponse.builder()
            .scheduleId(schedule.getId())
            .dayIndex(schedule.getDayIndex())
            .startTime(schedule.getStartTime().toString())
            .endTime(schedule.getEndTime().toString())
            .roomPlaceId(schedule.getRoomPlace() != null ? schedule.getRoomPlace().getId() : null)
            .memo(schedule.getMemo())
            .build();
    }
}
