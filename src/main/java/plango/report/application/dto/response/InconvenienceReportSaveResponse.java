package plango.report.application.dto.response;

import lombok.Builder;

public record InconvenienceReportSaveResponse(
        Long reportId
) {

    @Builder
    public InconvenienceReportSaveResponse {
    }
}
