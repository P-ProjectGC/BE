package plango.notice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "공지사항 상세 응답")
public record NoticeResponse(
        Long id,
        String title,
        String content,
        Long adminId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    @Builder
    public NoticeResponse { }
}
