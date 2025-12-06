package plango.notice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.application.mapper.NoticeMapper;
import plango.notice.domain.entity.Notice;
import plango.notice.domain.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
public class AdminCreateNoticeUseCase {

    private static final Logger log = LoggerFactory.getLogger(AdminCreateNoticeUseCase.class);

    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponse execute(NoticeCreateRequest request) {
        // ⭐ 테스트용: 일단 관리자 ID를 1로 고정
        Long adminId = 1L;

        log.info("Create notice by adminId={}", adminId);

        Notice notice = NoticeMapper.toEntity(request, adminId);
        Notice saved = noticeRepository.save(notice);

        return NoticeMapper.toResponse(saved);
    }
}
