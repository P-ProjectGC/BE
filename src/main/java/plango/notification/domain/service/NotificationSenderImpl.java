package plango.notification.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notification.domain.entity.Notification;
import plango.notification.domain.entity.NotificationType;
import plango.notification.domain.repository.NotificationRepository;
import plango.notification.domain.service.NotificationSender;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationSenderImpl implements NotificationSender {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendTripReminder(Long memberId, Long roomId, String roomTitle) {
        Notification notification = Notification.builder()
                .memberId(memberId)
                .type(NotificationType.TRIP_REMINDER)
                .title("여행 리마인드")
                .message(roomTitle + " 여행이 내일 시작됩니다!")
                .relatedRoomId(roomId)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void sendFriendRequest(Long toMemberId, Long fromMemberId) {

        Notification notification = Notification.builder()
                .memberId(toMemberId)
                .type(NotificationType.FRIEND_REQUEST)
                .title("친구 요청")
                .message("새로운 친구 요청이 도착했습니다.")
                .relatedMemberId(fromMemberId)
                .build();

        notificationRepository.save(notification);
    }
}
