package plango.notice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "공지사항 수정 요청")
public record NoticeUpdateRequest(
        @Schema(description = "공지 제목", example = "서비스 점검 시간 변경 안내")
        String title,

        @Schema(description = "공지 내용", example = "점검 시간이 23시에서 24시로 변경되었습니다.")
        String content
) {
    @Builder
    public NoticeUpdateRequest { }
}
