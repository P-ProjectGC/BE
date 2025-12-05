package plango.admin.application.dto.request;

import lombok.Builder;

public record AdminMemberUpdateRequest(
        String email,
        String nickname
) {

    @Builder
    public AdminMemberUpdateRequest {
    }
}
