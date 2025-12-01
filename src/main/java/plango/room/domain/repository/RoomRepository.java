package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.Room;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStartDate(LocalDate startDate);
}
