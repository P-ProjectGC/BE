package plango.notification.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;
import plango.member.domain.entity.Member;

@Entity
@Table(name = "notification_setting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationSetting extends BaseTimeEntity {

    /**
     * PK = member_id
     * 직접 값 세팅하는 방식 (AUTO_INCREMENT 아님)
     */
    @Id
    @Column(name = "member_id")
    private Long memberId;

    /**
     * Member 연관관계는 조회용으로만 사용
     * (insertable=false, updatable=false 로 PK와 값 공유)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "all_chat_room_enabled", nullable = false)
    private boolean allChatRoomEnabled;

    @Column(name = "trip_reminder_enabled", nullable = false)
    private boolean tripReminderEnabled;

    @Column(name = "friend_request_enabled", nullable = false)
    private boolean friendRequestEnabled;

    @Builder
    private NotificationSetting(Long memberId,
                                boolean allChatRoomEnabled,
                                boolean tripReminderEnabled,
                                boolean friendRequestEnabled) {
        this.memberId = memberId;
        this.allChatRoomEnabled = allChatRoomEnabled;
        this.tripReminderEnabled = tripReminderEnabled;
        this.friendRequestEnabled = friendRequestEnabled;
    }

    public void update(boolean allChatRoomEnabled,
                       boolean tripReminderEnabled,
                       boolean friendRequestEnabled) {
        this.allChatRoomEnabled = allChatRoomEnabled;
        this.tripReminderEnabled = tripReminderEnabled;
        this.friendRequestEnabled = friendRequestEnabled;
    }

    /**
     * 기본값 생성 팩토리
     * 반드시 memberId를 넘겨서 PK를 채우도록 강제
     */
    public static NotificationSetting createDefault(Long memberId) {
        return NotificationSetting.builder()
                .memberId(memberId)
                .allChatRoomEnabled(true)
                .tripReminderEnabled(true)
                .friendRequestEnabled(true)
                .build();
    }
}
