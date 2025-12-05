package plango.admin.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record AdminMemberSummaryResponse(
        Long memberId,
        String loginId,
        String email,
        String nickname,
        String loginType,
        LocalDateTime createdAt
) {

    @Builder
    public AdminMemberSummaryResponse {
    }
}
