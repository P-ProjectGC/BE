package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import plango.room.domain.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // 전체 여행방 개수 조회
    long count();

    // 오늘 생성된 여행방 수 조회 등에 사용하는 메서드
    // createdAt (LocalDateTime) 기준으로 카운트
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

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

    // 연/월별 여행방 수 (필요하면 사용)
    @Query("""
        select count(r)
        from Room r
        where year(r.createdAt) = :year
          and month(r.createdAt) = :month
        """)
    long countByYearAndMonth(@Param("year") int year, @Param("month") int month);

    // 대시보드: 월별 여행방 수 통계용 프로젝션
    interface MonthlyRoomStats {
        String getYearMonth();
        Long getTotalRoomCount();
    }

    // 대시보드: 월별 여행방 수 통계 조회
    @Query(value = """
        select DATE_FORMAT(r.created_at, '%Y-%m') as yearMonth,
               count(r.id) as totalRoomCount
        from room r
        group by DATE_FORMAT(r.created_at, '%Y-%m')
        order by DATE_FORMAT(r.created_at, '%Y-%m')
        """, nativeQuery = true)
    List<MonthlyRoomStats> countMonthlyRooms();
}
