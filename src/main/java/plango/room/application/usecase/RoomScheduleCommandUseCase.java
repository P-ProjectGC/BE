package plango.room.application.usecase;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.application.dto.request.ScheduleCreateRequest;
import plango.room.application.dto.response.ScheduleCreateResponse;
import plango.room.application.mapper.RoomScheduleMapper;
import plango.room.domain.entity.RoomSchedule;
import plango.room.domain.service.RoomScheduleService;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomScheduleCommandUseCase {

    private final RoomScheduleService roomScheduleService;

    public ScheduleCreateResponse createSchedule(Long roomId, ScheduleCreateRequest request) {

        RoomSchedule schedule = roomScheduleService.createSchedule(
            roomId,
            request.roomPlaceId(),
            request.dayIndex(),
            LocalTime.parse(request.startTime()),
            LocalTime.parse(request.endTime()),
            request.memo()
        );

        return RoomScheduleMapper.toCreateResponse(schedule);
    }

    public void updateSchedule(Long scheduleId,
                               String startTime,
                               String endTime,
                               String memo) {

        LocalTime start = startTime != null ? LocalTime.parse(startTime) : null;
        LocalTime end = endTime != null ? LocalTime.parse(endTime) : null;

        roomScheduleService.updateSchedule(scheduleId, start, end, memo);
    }

    public void deleteSchedule(Long scheduleId) {
        roomScheduleService.deleteSchedule(scheduleId);
    }
}

