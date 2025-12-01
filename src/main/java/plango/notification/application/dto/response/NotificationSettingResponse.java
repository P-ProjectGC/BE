package plango.notification.application.dto.response;

import lombok.Builder;
import plango.notification.domain.entity.NotificationSetting;

public record NotificationSettingResponse(
        boolean allChatRoomEnabled,
        boolean tripReminderEnabled,
        boolean friendRequestEnabled
) {

    @Builder
    public NotificationSettingResponse {}

    public static NotificationSettingResponse from(NotificationSetting setting) {
        return NotificationSettingResponse.builder()
                .allChatRoomEnabled(setting.isAllChatRoomEnabled())
                .tripReminderEnabled(setting.isTripReminderEnabled())
                .friendRequestEnabled(setting.isFriendRequestEnabled())
                .build();
    }
}
