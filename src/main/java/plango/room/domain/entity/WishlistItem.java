package plango.room.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishlistItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String placeName;

    private String address;

    private String category;

    private String memo;

    private Integer dayIndex;

    private Integer orderIndex;

    public WishlistItem(
            Room room,
            String placeName,
            String address,
            String category,
            String memo,
            Integer dayIndex,
            Integer orderIndex
    ) {
        this.room = room;
        this.placeName = placeName;
        this.address = address;
        this.category = category;
        this.memo = memo;
        this.dayIndex = dayIndex;
        this.orderIndex = orderIndex;
    }

    void setRoom(Room room) {
        this.room = room;
    }
}
