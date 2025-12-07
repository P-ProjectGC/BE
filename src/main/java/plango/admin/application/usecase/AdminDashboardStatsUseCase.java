package plango.admin.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.admin.application.dto.response.AdminDashboardStatsResponse;
import plango.member.domain.repository.MemberRepository;
import plango.notice.domain.repository.NoticeRepository;
import plango.report.domain.repository.InconvenienceReportRepository;
import plango.room.domain.repository.RoomRepository;
import plango.room.domain.repository.RoomScheduleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardStatsUseCase {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final RoomScheduleRepository roomScheduleRepository;
    private final NoticeRepository noticeRepository;
    private final InconvenienceReportRepository inconvenienceReportRepository;

    public AdminDashboardStatsResponse execute() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1)
                .atStartOfDay();

        long totalMemberCount = memberRepository.count();
        long todayNewMemberCount =
                memberRepository.countByCreatedAtBetween(startOfToday, endOfToday);

        long totalRoomCount = roomRepository.count();
        long todayNewRoomCount =
                roomRepository.countByCreatedAtBetween(startOfToday, endOfToday);

        long totalScheduleCount = roomScheduleRepository.count();
        long todayNewScheduleCount =
                roomScheduleRepository.countByCreatedAtBetween(startOfToday, endOfToday);

        long totalNoticeCount = noticeRepository.count();

        long totalInconvenienceReportCount = inconvenienceReportRepository.count();

        return AdminDashboardStatsResponse.builder()
                .totalMemberCount(totalMemberCount)
                .todayNewMemberCount(todayNewMemberCount)
                .totalRoomCount(totalRoomCount)
                .todayNewRoomCount(todayNewRoomCount)
                .totalScheduleCount(totalScheduleCount)
                .todayNewScheduleCount(todayNewScheduleCount)
                .totalNoticeCount(totalNoticeCount)
                .totalInconvenienceReportCount(totalInconvenienceReportCount)
                .build();
    }
}
