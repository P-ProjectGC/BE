package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
