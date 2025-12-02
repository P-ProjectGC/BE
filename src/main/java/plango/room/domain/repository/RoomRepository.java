package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import plango.room.domain.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // 특정 시작일 기준으로 여행방 조회 (기존 메서드 유지)
    List<Room> findByStartDate(LocalDate startDate);

    // 특정 멤버가 속한 모든 여행방 조회
    @Query("""
        select distinct r
        from Room r
        join RoomMember rm
            on rm.room = r
        where rm.member.id = :memberId
        order by r.startDate asc
        """)
    List<Room> findByMemberId(@Param("memberId") Long memberId);

    // 특정 멤버가 속한 여행방 중, 방 이름/메모로 필터링
    @Query("""
        select distinct r
        from Room r
        join RoomMember rm
            on rm.room = r
        where rm.member.id = :memberId
        and (
            :keyword is null
            or :keyword = ''
            or r.name like concat('%', :keyword, '%')
            or r.memo like concat('%', :keyword, '%')
        )
        order by r.startDate asc
        """)
    List<Room> findByMemberIdAndKeyword(
            @Param("memberId") Long memberId,
            @Param("keyword") String keyword
    );
}
