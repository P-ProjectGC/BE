package plango.room.application.mapper;

import lombok.experimental.UtilityClass;
import plango.room.application.dto.response.ScheduleCreateResponse;
import plango.room.domain.entity.RoomPlace;
import plango.room.domain.entity.RoomSchedule;

@UtilityClass
public class RoomScheduleMapper {

    public ScheduleCreateResponse toCreateResponse(RoomSchedule schedule) {
        RoomPlace place = schedule.getRoomPlace();

        return ScheduleCreateResponse.builder()
                .scheduleId(schedule.getId())
                .dayIndex(schedule.getDayIndex())
                .startTime(schedule.getStartTime().toString())
                .endTime(schedule.getEndTime().toString())
                .roomPlaceId(place != null ? place.getId() : null)
                .memo(schedule.getMemo())
                .placeName(place != null ? place.getName() : null)
                .address(place != null ? place.getAddress() : null)
                .lat(place != null ? place.getLatitude() : null)
                .lng(place != null ? place.getLongitude() : null)
                .build();
    }
}
