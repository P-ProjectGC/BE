package plango.notice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.request.NoticeUpdateRequest;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class AdminUpdateNoticeUseCase {

    private final NoticeService noticeService;

    @Transactional
    public void execute(Long noticeId, NoticeUpdateRequest request) {
        noticeService.update(
                noticeId,
                request.title(),
                request.content()
        );
    }
}
