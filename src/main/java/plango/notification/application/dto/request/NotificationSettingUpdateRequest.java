package plango.notification.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record NotificationSettingUpdateRequest(
        @NotNull Boolean allChatRoomEnabled,
        @NotNull Boolean tripReminderEnabled,
        @NotNull Boolean friendRequestEnabled
) {

    @Builder
    public NotificationSettingUpdateRequest {}
}
