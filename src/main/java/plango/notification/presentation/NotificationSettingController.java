package plango.notification.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.notification.application.dto.request.NotificationSettingUpdateRequest;
import plango.notification.application.dto.response.NotificationSettingResponse;
import plango.notification.application.usecase.NotificationSettingUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/me/notifications")
public class NotificationSettingController {

    private final NotificationSettingUseCase notificationSettingUseCase;

    @GetMapping
    public CommonResponse<NotificationSettingResponse> getMyNotificationSetting(
            @RequestHeader("X-MEMBER-ID") Long memberId
    ) {
        NotificationSettingResponse response =
                notificationSettingUseCase.getMyNotificationSetting(memberId);

        return CommonResponse.success(
                ResponseMessage.NOTIFICATION_SETTING_READ_SUCCESS,
                response
        );
    }

    @PatchMapping
    public CommonResponse<NotificationSettingResponse> updateMyNotificationSetting(
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @RequestBody @Valid NotificationSettingUpdateRequest request
    ) {
        NotificationSettingResponse response =
                notificationSettingUseCase.updateMyNotificationSetting(memberId, request);

        return CommonResponse.success(
                ResponseMessage.NOTIFICATION_SETTING_UPDATE_SUCCESS,
                response
        );
    }
}
