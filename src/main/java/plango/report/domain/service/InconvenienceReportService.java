package plango.report.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.report.domain.entity.InconvenienceReport;
import plango.report.domain.entity.InconvenienceReportStatus;
import plango.report.domain.repository.InconvenienceReportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InconvenienceReportService {

    private final InconvenienceReportRepository repository;

    /**
     * 상태 + 최신순 정렬로 전체 신고 목록 조회
     */
    public List<InconvenienceReport> getAllOrdered() {
        return repository.findAllByOrderByStatusAscCreatedAtDesc();
    }

    /**
     * ID 기반 단건 조회
     */
    public InconvenienceReport getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPORT_NOT_FOUND));
    }

    /**
     * 상태 변경
     */
    @Transactional
    public void changeStatus(Long id, InconvenienceReportStatus status, String adminMemo) {
        InconvenienceReport report = getById(id);
        report.changeStatus(status, adminMemo);
    }
}
