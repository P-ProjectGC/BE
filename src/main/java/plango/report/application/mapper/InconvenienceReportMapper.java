package plango.report.application.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import plango.member.domain.entity.Member;
import plango.report.application.dto.request.InconvenienceReportSaveRequest;
import plango.report.application.dto.response.InconvenienceReportSaveResponse;
import plango.report.domain.entity.InconvenienceReport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InconvenienceReportMapper {

    public static InconvenienceReport toEntity(
            Member member,
            InconvenienceReportSaveRequest request
    ) {
        return new InconvenienceReport(member, request.content());
    }

    public static InconvenienceReportSaveResponse toResponse(
            InconvenienceReport report
    ) {
        return InconvenienceReportSaveResponse.builder()
                .reportId(report.getId())
                .build();
    }
}
