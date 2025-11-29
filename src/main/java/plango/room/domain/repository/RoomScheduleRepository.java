package plango.room.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomSchedule;

public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {

    List<RoomSchedule> findAllByRoomIdAndDayIndexOrderByStartTimeAsc(Long roomId, int dayIndex);
}
