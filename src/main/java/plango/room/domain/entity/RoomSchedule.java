package plango.room.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_place_id")
    private RoomPlace roomPlace;

    @Column(name = "day_index", nullable = false)
    private int dayIndex;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(length = 255)
    private String memo;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    private RoomSchedule(Room room,
                         RoomPlace roomPlace,
                         int dayIndex,
                         LocalTime startTime,
                         LocalTime endTime,
                         String memo,
                         int sortOrder) {

        this.room = room;
        this.roomPlace = roomPlace;
        this.dayIndex = dayIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.memo = memo;
        this.sortOrder = sortOrder;
    }

    public void updateTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updatePlace(RoomPlace roomPlace) {
        this.roomPlace = roomPlace;
    }

    public void changeSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}

