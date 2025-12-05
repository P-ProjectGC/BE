package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.admin.application.dto.response.AdminReportDetailResponse;
import plango.admin.application.mapper.AdminReportMapper;
import plango.report.domain.service.InconvenienceReportService;

@Service
@RequiredArgsConstructor
public class AdminGetReportDetailUseCase {

    private final InconvenienceReportService reportService;

    public AdminReportDetailResponse execute(Long reportId) {
        return AdminReportMapper.toDetail(reportService.getById(reportId));
    }
}
