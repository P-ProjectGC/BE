package plango.notice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.domain.service.NoticeService;

@Service
@RequiredArgsConstructor
public class AdminDeleteNoticeUseCase {

    private final NoticeService noticeService;

    @Transactional
    public void execute(Long noticeId) {
        noticeService.delete(noticeId);
    }
}
