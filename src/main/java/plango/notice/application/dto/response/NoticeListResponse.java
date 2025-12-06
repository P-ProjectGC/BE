package plango.notice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "공지사항 리스트 응답")
public record NoticeListResponse(
        Long id,
        String title,
        LocalDateTime createdAt
) {
    @Builder
    public NoticeListResponse { }
}
