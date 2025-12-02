package plango.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import plango.notification.domain.service.NotificationSender;
import plango.notification.domain.repository.NotificationSettingRepository;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.repository.RoomRepository;
import plango.room.domain.repository.RoomMemberRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TripReminderScheduler {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationSender notificationSender;

    /**
     * 매일 새벽 3시에 여행 시작 하루 전 리마인드 알림 발송
     */
    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    @Transactional
    public void sendTripReminder() {

        LocalDate tomorrow = LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1);

        // 1) 내일 출발하는 여행방 찾기
        List<Room> rooms = roomRepository.findByStartDate(tomorrow);

        // 2) 방마다 멤버에게 리마인드 전송
        for (Room room : rooms) {

            List<RoomMember> roomMembers = roomMemberRepository.findByRoom_Id(room.getId());

            for (RoomMember roomMember : roomMembers) {
                Long memberId = roomMember.getMember().getId();

                notificationSettingRepository.findById(memberId)
                        .filter(setting -> setting.isTripReminderEnabled())
                        .ifPresent(setting -> notificationSender.sendTripReminder(
                                memberId,
                                room.getId(),
                                null
                        ));
            }
        }
    }
}
