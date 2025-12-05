package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.admin.application.dto.response.AdminReportSummaryResponse;
import plango.admin.application.mapper.AdminReportMapper;
import plango.report.domain.entity.InconvenienceReport;
import plango.report.domain.service.InconvenienceReportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGetReportListUseCase {

    private final InconvenienceReportService reportService;

    public List<AdminReportSummaryResponse> execute() {
        List<InconvenienceReport> reports = reportService.getAllOrdered();
        return AdminReportMapper.toSummaries(reports);
    }
}
