package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomMember;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByRoom(plango.room.domain.entity.Room room);

    void deleteByRoomAndMemberIdIn(plango.room.domain.entity.Room room, Set<Long> memberIds);

    boolean existsByRoomAndMemberId(plango.room.domain.entity.Room room, Long memberId);

    List<RoomMember> findByRoomId(Long roomId);
}
