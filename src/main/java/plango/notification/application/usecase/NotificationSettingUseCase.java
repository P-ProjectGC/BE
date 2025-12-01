package plango.notification.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.notification.application.dto.request.NotificationSettingUpdateRequest;
import plango.notification.application.dto.response.NotificationSettingResponse;
import plango.notification.domain.entity.NotificationSetting;
import plango.notification.domain.repository.NotificationSettingRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationSettingUseCase {

    private final NotificationSettingRepository notificationSettingRepository;
    private final MemberRepository memberRepository;

    public NotificationSettingResponse getMyNotificationSetting(Long memberId) {
        NotificationSetting setting = notificationSettingRepository.findById(memberId)
                .orElseGet(() -> createDefaultSetting(memberId));

        return NotificationSettingResponse.from(setting);
    }

    @Transactional
    public NotificationSettingResponse updateMyNotificationSetting(
            Long memberId,
            NotificationSettingUpdateRequest request
    ) {
        NotificationSetting setting = notificationSettingRepository.findById(memberId)
                .orElseGet(() -> createDefaultSetting(memberId));

        setting.update(
                request.allChatRoomEnabled(),
                request.tripReminderEnabled(),
                request.friendRequestEnabled()
        );

        return NotificationSettingResponse.from(setting);
    }

    @Transactional
    protected NotificationSetting createDefaultSetting(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        NotificationSetting setting = NotificationSetting.createDefault(member.getId());

        return notificationSettingRepository.save(setting);
    }
}
