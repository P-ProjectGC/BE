package plango.room.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomPlace;

public interface RoomPlaceRepository extends JpaRepository<RoomPlace, Long> {

    List<RoomPlace> findAllByRoomIdAndDeletedFalseOrderByIdAsc(Long roomId);
}
