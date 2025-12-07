package plango.notice.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.entity.NoticeType;
import plango.notice.domain.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Notice getById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
    }

    public List<Notice> getAll() {
        return noticeRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public void delete(Long noticeId) {
        Notice notice = getById(noticeId);
        noticeRepository.delete(notice);
    }

    @Transactional
    public void update(
            Long noticeId,
            String title,
            String content,
            NoticeType type
    ) {
        Notice notice = getById(noticeId);
        notice.update(title, content, type);
    }
}
