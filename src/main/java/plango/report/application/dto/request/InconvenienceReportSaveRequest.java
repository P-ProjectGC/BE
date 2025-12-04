package plango.report.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record InconvenienceReportSaveRequest(
        @NotBlank(message = "불편사항 내용을 입력해주세요.")
        String content
) {

    @Builder
    public InconvenienceReportSaveRequest {
    }
}
