package plango.notification.domain.service;

public interface NotificationSender {

    void sendTripReminder(Long memberId, Long roomId, String roomTitle);
    void sendFriendRequest(Long toMemberId, Long fromMemberId);

}
