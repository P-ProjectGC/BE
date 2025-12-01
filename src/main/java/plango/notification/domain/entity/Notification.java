package plango.notification.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false, length = 255)
    private String message;

    @Column(name = "related_room_id")
    private Long relatedRoomId;

    @Column(name = "related_member_id")
    private Long relatedMemberId;

    @Builder
    public Notification(Long memberId,
                        NotificationType type,
                        String title,
                        String message,
                        Long relatedRoomId,
                        Long relatedMemberId) {
        this.memberId = memberId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.relatedRoomId = relatedRoomId;
        this.relatedMemberId = relatedMemberId;
    }
}
