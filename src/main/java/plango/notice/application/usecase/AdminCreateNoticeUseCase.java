package plango.notice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.application.mapper.NoticeMapper;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class AdminCreateNoticeUseCase {

    private final NoticeService noticeService;

    @Transactional
    public NoticeResponse execute(NoticeCreateRequest request) {
        Notice notice = NoticeMapper.toEntity(request);

        Notice saved = noticeService.save(notice);

        return NoticeMapper.toResponse(saved);
    }
}
