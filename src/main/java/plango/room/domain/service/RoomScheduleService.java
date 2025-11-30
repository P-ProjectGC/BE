package plango.room.domain.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomPlace;
import plango.room.domain.entity.RoomSchedule;
import plango.room.domain.repository.RoomPlaceRepository;
import plango.room.domain.repository.RoomRepository;
import plango.room.domain.repository.RoomScheduleRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomScheduleService {

    private final RoomRepository roomRepository;
    private final RoomPlaceRepository roomPlaceRepository;
    private final RoomScheduleRepository roomScheduleRepository;

    public RoomSchedule createSchedule(
            Long roomId,
            Long roomPlaceId,
            int dayIndex,
            LocalTime startTime,
            LocalTime endTime,
            String memo
    ) {

        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        room.validateDayIndex(dayIndex);

        RoomPlace roomPlace = null;
        if (roomPlaceId != null) {
            roomPlace = roomPlaceRepository.findById(roomPlaceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_PLACE_NOT_FOUND));
        }

        int sortOrder = calculateNextSortOrder(roomId, dayIndex);

        RoomSchedule roomSchedule = RoomSchedule.builder()
            .room(room)
            .roomPlace(roomPlace)
            .dayIndex(dayIndex)
            .startTime(startTime)
            .endTime(endTime)
            .memo(memo)
            .sortOrder(sortOrder)
            .build();

        return roomScheduleRepository.save(roomSchedule);
    }

    @Transactional(readOnly = true)
    public List<RoomSchedule> getSchedules(Long roomId, int dayIndex) {

        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        room.validateDayIndex(dayIndex);

        return roomScheduleRepository.findAllByRoomIdAndDayIndexOrderByStartTimeAsc(roomId, dayIndex);
    }

    public void updateSchedule(Long scheduleId,
                               LocalTime startTime,
                               LocalTime endTime,
                               String memo) {

        RoomSchedule roomSchedule = roomScheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_SCHEDULE_NOT_FOUND));

        if (startTime != null && endTime != null) {
            roomSchedule.updateTime(startTime, endTime);
        }

        if (memo != null) {
            roomSchedule.updateMemo(memo);
        }
    }

    public void deleteSchedule(Long scheduleId) {

        RoomSchedule roomSchedule = roomScheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_SCHEDULE_NOT_FOUND));

        roomScheduleRepository.delete(roomSchedule);
    }

    private int calculateNextSortOrder(Long roomId, int dayIndex) {
        List<RoomSchedule> schedules = roomScheduleRepository
            .findAllByRoomIdAndDayIndexOrderByStartTimeAsc(roomId, dayIndex);

        return schedules.size() + 1;
    }
}
