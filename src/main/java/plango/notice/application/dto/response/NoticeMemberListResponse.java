package plango.notice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import plango.notice.domain.entity.NoticeType;

@Schema(description = "사용자 공지사항 리스트 응답")
public record NoticeMemberListResponse(
        @Schema(description = "공지 ID", example = "1")
        Long id,

        @Schema(description = "공지 제목", example = "서비스 점검 안내")
        String title,

        @Schema(
                description = "공지 타입",
                example = "UPDATE",
                allowableValues = {"ERROR", "UPDATE", "EMERGENCY"}
        )
        NoticeType type,

        @Schema(
                description = "공지 내용",
                example = "금일 23시부터 서버 점검이 진행됩니다."
        )
        String content,

        @Schema(description = "생성 일시", example = "2025-12-08T12:34:56")
        LocalDateTime createdAt
) {
    @Builder
    public NoticeMemberListResponse {
    }
}
