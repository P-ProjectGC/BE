package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.admin.application.dto.request.AdminReportUpdateStatusRequest;
import plango.report.domain.entity.InconvenienceReportStatus;
import plango.report.domain.service.InconvenienceReportService;

@Service
@RequiredArgsConstructor
public class AdminUpdateReportStatusUseCase {

    private final InconvenienceReportService reportService;

    @Transactional
    public void execute(Long reportId, AdminReportUpdateStatusRequest request) {
        InconvenienceReportStatus status = InconvenienceReportStatus.valueOf(request.status());
        reportService.changeStatus(reportId, status, request.adminMemo());
    }
}
