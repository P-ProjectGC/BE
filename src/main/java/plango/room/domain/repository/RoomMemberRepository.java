package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomRole;
import java.util.List;
import java.util.Set;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByRoom(Room room);

    void deleteByRoomAndMemberIdIn(Room room, Set<Long> memberIds);

    boolean existsByRoomAndMemberId(Room room, Long memberId);

    List<RoomMember> findByRoom_Id(Long roomId);

    boolean existsByRoom_IdAndMemberIdAndRole(Long roomId, Long memberId, RoomRole role);

    java.util.Optional<RoomMember> findByRoom_IdAndRole(Long roomId, RoomRole role);

    java.util.Optional<RoomMember> findByRoom_IdAndMemberId(Long roomId, Long memberId);
}
