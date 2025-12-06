package plango.admin.application.mapper;

import java.util.List;
import plango.admin.application.dto.response.AdminReportDetailResponse;
import plango.admin.application.dto.response.AdminReportSummaryResponse;
import plango.report.domain.entity.InconvenienceReport;

public class AdminReportMapper {

    private AdminReportMapper() {
    }

    public static AdminReportSummaryResponse toSummary(InconvenienceReport report) {
        return AdminReportSummaryResponse.builder()
                .reportId(report.getId())
                .memberId(report.getMember().getId())
                .memberNickname(report.getMember().getNickname())
                .status(report.getStatus().name())
                .createdAt(report.getCreatedAt())
                .build();
    }

    public static List<AdminReportSummaryResponse> toSummaries(
            List<InconvenienceReport> reports
    ) {
        return reports.stream()
                .map(AdminReportMapper::toSummary)
                .toList();
    }

    public static AdminReportDetailResponse toDetail(InconvenienceReport report) {
        return AdminReportDetailResponse.builder()
                .reportId(report.getId())
                .memberId(report.getMember().getId())
                .memberLoginId(report.getMember().getLoginId())
                .memberNickname(report.getMember().getNickname())
                .content(report.getContent())
                .status(report.getStatus().name())
                .adminMemo(report.getAdminMemo())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }
}
