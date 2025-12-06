package plango.notice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.application.mapper.NoticeMapper;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class AdminGetNoticeDetailUseCase {

    private final NoticeService noticeService;

    @Transactional(readOnly = true)
    public NoticeResponse execute(Long noticeId) {
        Notice notice = noticeService.getById(noticeId);

        return NoticeMapper.toResponse(notice);
    }
}
