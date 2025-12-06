package plango.notice.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.response.NoticeListResponse;
import plango.notice.application.mapper.NoticeMapper;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class AdminGetNoticeListUseCase {

    private final NoticeService noticeService;

    @Transactional(readOnly = true)
    public List<NoticeListResponse> execute() {
        List<Notice> notices = noticeService.getAll();

        return NoticeMapper.toListResponses(notices);
    }
}
