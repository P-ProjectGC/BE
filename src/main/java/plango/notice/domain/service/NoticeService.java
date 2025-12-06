package plango.notice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.repository.NoticeRepository;

import java.util.List;

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
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Transactional
    public void delete(Long noticeId) {
        Notice notice = getById(noticeId);
        noticeRepository.delete(notice);
    }

    @Transactional
    public void update(Long noticeId, String title, String content) {
        Notice notice = getById(noticeId);
        notice.update(title, content);
    }
}
