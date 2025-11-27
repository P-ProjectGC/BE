package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.RoomMember;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
}
