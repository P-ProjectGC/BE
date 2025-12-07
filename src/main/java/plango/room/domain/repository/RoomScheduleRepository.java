package plango.room.domain.repository;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomSchedule;

public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {

    List<RoomSchedule> findAllByRoomIdAndDayIndexOrderByStartTimeAsc(Long roomId, int dayIndex);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
