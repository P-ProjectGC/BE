package plango.report.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.report.application.dto.request.InconvenienceReportSaveRequest;
import plango.report.application.dto.response.InconvenienceReportSaveResponse;
import plango.report.application.usecase.InconvenienceReportSaveUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class InconvenienceReportController {

    private final InconvenienceReportSaveUseCase inconvenienceReportSaveUseCase;

    @PostMapping("/inconvenience")
    //@Operation(summary = "불편사항 신고", description = "프로필 화면에서 불편사항을 신고합니다.")
    public ResponseEntity<CommonResponse<InconvenienceReportSaveResponse>> reportInconvenience(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody InconvenienceReportSaveRequest request
    ) {
        InconvenienceReportSaveResponse response =
                inconvenienceReportSaveUseCase.execute(memberId, request);

        return ResponseEntity.ok(
                CommonResponse.success(
                        ResponseMessage.SUCCESS,
                        response
                )
        );
    }
}
