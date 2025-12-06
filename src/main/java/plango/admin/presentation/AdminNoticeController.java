package plango.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.request.NoticeUpdateRequest;
import plango.notice.application.dto.response.NoticeListResponse;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.application.usecase.AdminCreateNoticeUseCase;
import plango.notice.application.usecase.AdminDeleteNoticeUseCase;
import plango.notice.application.usecase.AdminGetNoticeDetailUseCase;
import plango.notice.application.usecase.AdminGetNoticeListUseCase;
import plango.notice.application.usecase.AdminUpdateNoticeUseCase;

import java.util.List;

@Tag(
        name = "관리자 공지사항 관리",
        description = "관리자가 공지사항을 생성, 수정, 삭제, 조회하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
public class AdminNoticeController {

    private final AdminCreateNoticeUseCase createNoticeUseCase;

    private final AdminUpdateNoticeUseCase updateNoticeUseCase;

    private final AdminDeleteNoticeUseCase deleteNoticeUseCase;

    private final AdminGetNoticeDetailUseCase getNoticeDetailUseCase;

    private final AdminGetNoticeListUseCase getNoticeListUseCase;

    @Operation(
            summary = "공지사항 생성",
            description = "관리자가 새로운 공지사항을 생성합니다."
    )
    @PostMapping
    public CommonResponse<NoticeResponse> createNotice(
            @RequestBody @Valid NoticeCreateRequest request
    ) {
        NoticeResponse response = createNoticeUseCase.execute(request);

        return CommonResponse.success(
                ResponseMessage.NOTICE_CREATE_SUCCESS,
                response
        );
    }

    @Operation(
            summary = "공지사항 목록 조회",
            description = "등록된 공지사항 목록을 최신순으로 조회합니다."
    )
    @GetMapping
    public CommonResponse<List<NoticeListResponse>> getNotices() {
        List<NoticeListResponse> responses = getNoticeListUseCase.execute();

        return CommonResponse.success(
                ResponseMessage.NOTICE_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "공지사항 상세 조회",
            description = "공지사항 ID를 기준으로 상세 내용을 조회합니다."
    )
    @GetMapping("/{noticeId}")
    public CommonResponse<NoticeResponse> getNoticeDetail(
            @PathVariable Long noticeId
    ) {
        NoticeResponse response = getNoticeDetailUseCase.execute(noticeId);

        return CommonResponse.success(
                ResponseMessage.NOTICE_DETAIL_GET_SUCCESS,
                response
        );
    }

    @Operation(
            summary = "공지사항 수정",
            description = "공지사항의 제목과 내용을 수정합니다."
    )
    @PatchMapping("/{noticeId}")
    public CommonResponse<Void> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody @Valid NoticeUpdateRequest request
    ) {
        updateNoticeUseCase.execute(noticeId, request);

        return CommonResponse.success(
                ResponseMessage.NOTICE_UPDATE_SUCCESS
        );
    }

    @Operation(
            summary = "공지사항 삭제",
            description = "공지사항을 삭제합니다."
    )
    @DeleteMapping("/{noticeId}")
    public CommonResponse<Void> deleteNotice(
            @PathVariable Long noticeId
    ) {
        deleteNoticeUseCase.execute(noticeId);

        return CommonResponse.success(
                ResponseMessage.NOTICE_DELETE_SUCCESS
        );
    }
}
