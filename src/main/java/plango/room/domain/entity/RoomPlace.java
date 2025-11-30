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

    @Column(name = "formatted_address", length = 255)
    private String formattedAddress;

    @Column(name = "google_place_id", length = 255)
    private String googlePlaceId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_by_member_id", nullable = false)
    private Long createdByMemberId;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    private RoomPlace(Room room,
                      String name,
                      String address,
                      String googlePlaceId,
                      String formattedAddress,
                      Double latitude,
                      Double longitude,
                      Long createdByMemberId) {

        this.room = room;
        this.name = name;
        this.address = address;
        this.googlePlaceId = googlePlaceId;
        this.formattedAddress = formattedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdByMemberId = createdByMemberId;
        this.deleted = false;
    }

    public void assignRoom(Room room) {
        this.room = room;
    }

    public void markDeleted() {
        this.deleted = true;
    }

    public void updatePlaceInfo(String name,
                                String address,
                                String formattedAddress,
                                String googlePlaceId,
                                Double latitude,
                                Double longitude) {
        this.name = name;
        this.address = address;
        this.formattedAddress = formattedAddress;
        this.googlePlaceId = googlePlaceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
