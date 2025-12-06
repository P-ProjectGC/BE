package plango.notice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "공지사항 생성 요청")
public record NoticeCreateRequest(
        @Schema(description = "공지 제목", example = "서비스 점검 안내")
        String title,

        @Schema(description = "공지 내용", example = "금일 23시부터 서버 점검이 진행됩니다.")
        String content
) {
    @Builder
    public NoticeCreateRequest { }
}
