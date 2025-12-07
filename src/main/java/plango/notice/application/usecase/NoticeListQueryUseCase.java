package plango.notice.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.response.NoticeMemberListResponse;
import plango.notice.application.mapper.NoticeMapper;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class NoticeListQueryUseCase {

    private final NoticeService noticeService;

    @Transactional(readOnly = true)
    public List<NoticeMemberListResponse> execute() {
        List<Notice> notices = noticeService.getAll();

        return NoticeMapper.toMemberListResponses(notices);
    }
}
