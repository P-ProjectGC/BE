package plango.room.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomPlace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(name = "created_by_member_id", nullable = false)
    private Long createdByMemberId;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    private RoomPlace(Room room,
                      String name,
                      String address,
                      Long createdByMemberId) {

        this.room = room;
        this.name = name;
        this.address = address;
        this.createdByMemberId = createdByMemberId;
        this.likeCount = 0;
        this.deleted = false;
    }

    public void assignRoom(Room room) {
        this.room = room;
    }

    public void markDeleted() {
        this.deleted = true;
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
