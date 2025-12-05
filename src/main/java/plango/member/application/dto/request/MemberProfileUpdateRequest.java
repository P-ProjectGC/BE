package plango.member.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record MemberProfileUpdateRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 50, message = "닉네임은 50자 이하여야 합니다.")
        String nickname,
        String profileImageUrl
) {
        @Builder
        public MemberProfileUpdateRequest {}
}
