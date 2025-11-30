package plango.room.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.application.dto.response.ScheduleCreateResponse;
import plango.room.application.mapper.RoomScheduleMapper;
import plango.room.domain.entity.RoomSchedule;
import plango.room.domain.service.RoomScheduleService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomScheduleQueryUseCase {

    private final RoomScheduleService roomScheduleService;

    public List<ScheduleCreateResponse> getSchedules(Long roomId, int dayIndex) {
        List<RoomSchedule> schedules = roomScheduleService.getSchedules(roomId, dayIndex);

        return schedules.stream()
            .map(RoomScheduleMapper::toCreateResponse)
            .toList();
    }
}
