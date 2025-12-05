package plango.admin.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record AdminMemberDetailResponse(
        Long memberId,
        String loginId,
        String email,
        String name,
        String nickname,
        String loginType,
        String profileImageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    @Builder
    public AdminMemberDetailResponse {
    }
}
