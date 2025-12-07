package plango.notice.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.notice.application.dto.response.NoticeMemberListResponse;
import plango.notice.application.usecase.NoticeListQueryUseCase;

@Tag(
        name = "사용자 공지사항",
        description = "사용자가 공지사항을 조회하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeListQueryUseCase noticeListQueryUseCase;

    @Operation(
            summary = "공지사항 목록 조회 (사용자)",
            description = "사용자가 전체 공지사항을 최신 순으로 조회합니다."
    )
    @GetMapping
    public CommonResponse<List<NoticeMemberListResponse>> getNoticeList() {
        List<NoticeMemberListResponse> responses = noticeListQueryUseCase.execute();

        return CommonResponse.success(
                ResponseMessage.NOTICE_LIST_GET_SUCCESS,
                responses
        );
    }
}
